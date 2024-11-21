-- Insertar tipos de usuario si no existen
INSERT INTO tipo_usuario (idtipo_usuario, nombre) VALUES (1, 'ADMIN') ON DUPLICATE KEY UPDATE nombre = 'ADMIN';
INSERT INTO tipo_usuario (idtipo_usuario, nombre) VALUES (2, 'ESTANDAR') ON DUPLICATE KEY UPDATE nombre = 'ESTANDAR';

-- Insertar usuario administrador por defecto
INSERT INTO usuario (nombre, correo, contraseña, tipo_usuario_id) 
VALUES ('Admin', 'admin@biblioteca.com', '$2a$10$7pZF7WE0J/r3puvZIlLtxOfmvjiMZzW21zY3EdVqFGiDoMNqH5c9S', 1) 
ON DUPLICATE KEY UPDATE correo = 'admin@biblioteca.com';