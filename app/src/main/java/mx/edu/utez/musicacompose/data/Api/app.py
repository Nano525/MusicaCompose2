from flask import Flask, jsonify, request
from flask_cors import CORS
from models import db, Album, Cancion
import os

app = Flask(__name__)
CORS(app)  # permite peticiones desde Android
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///database.db'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
app.config['UPLOAD_FOLDER'] = 'uploads'

db.init_app(app)

# Crear BD al inicio
with app.app_context():
    if not os.path.exists('database.db'):
        db.create_all()

# ----------------------------
# GET: Obtener todos los álbumes con sus canciones
# ----------------------------
@app.route('/albums', methods=['GET'])
def get_albums():
    albums = Album.query.all()
    data = []
    for album in albums:
        canciones = Cancion.query.filter_by(albumId=album.id).all()
        data.append({
            'album': {
                'id': album.id,
                'nombre': album.nombre,
                'artista': album.artista,
                'imagen': album.imagen
            },
            'canciones': [
                {
                    'id': c.id,
                    'nombre': c.nombre,
                    'artista': c.artista,
                    'duracion': c.duracion,
                    'genero': c.genero,
                    'albumId': c.albumId
                } for c in canciones
            ]
        })
    return jsonify(data)

# ----------------------------
# POST: Crear un álbum con multipart (nombre, artista, imagen, canciones)
# ----------------------------
@app.route('/albums', methods=['POST'])
def create_album():
    nombre = request.form.get('nombre')
    artista = request.form.get('artista')
    imagen = request.files.get('imagen')

    if not nombre or not artista:
        return jsonify({'error': 'Faltan datos del álbum'}), 400

    # Guardar imagen si existe
    image_filename = None
    if imagen:
        os.makedirs(app.config['UPLOAD_FOLDER'], exist_ok=True)
        image_filename = os.path.join(app.config['UPLOAD_FOLDER'], imagen.filename)
        imagen.save(image_filename)

    nuevo_album = Album(nombre=nombre, artista=artista, imagen=image_filename)
    db.session.add(nuevo_album)
    db.session.commit()

    # Registrar canciones (opcional)
    canciones_json = request.form.get('canciones')
    if canciones_json:
        import json
        canciones = json.loads(canciones_json)
        for c in canciones:
            nueva_cancion = Cancion(
                nombre=c.get('nombre'),
                artista=c.get('artista'),
                duracion=c.get('duracion'),
                genero=c.get('genero'),
                albumId=nuevo_album.id
            )
            db.session.add(nueva_cancion)
        db.session.commit()

    return jsonify({'message': 'Álbum creado correctamente', 'id': nuevo_album.id}), 201

if __name__ == '__main__':
    app.run(debug=True)
