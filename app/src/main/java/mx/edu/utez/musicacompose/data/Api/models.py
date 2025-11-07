from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

class Album(db.Model):
    __tablename__ = 'albums'
    id = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    artista = db.Column(db.String(100), nullable=False)
    imagen = db.Column(db.String(200))  # ruta o nombre de archivo

    canciones = db.relationship('Cancion', backref='album', cascade="all, delete")

class Cancion(db.Model):
    __tablename__ = 'canciones'
    id = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    artista = db.Column(db.String(100), nullable=False)
    duracion = db.Column(db.String(20))
    genero = db.Column(db.String(50))
    albumId = db.Column(db.Integer, db.ForeignKey('albums.id'), nullable=False)
