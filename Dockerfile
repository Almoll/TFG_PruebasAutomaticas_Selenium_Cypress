# Imagen base con Chrome + soporte de Java
FROM seleniarm/standalone-chromium:latest

# Usuario root para instalar Node
USER root

# Instalar Node.js y npm (para Cypress)
RUN apt-get update && \
    apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_20.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g npm@latest

# Crear directorio de trabajo
WORKDIR /workspace

# Copiar los proyectos
COPY Selenium ./Selenium
COPY cipress ./cipress

# Instalar dependencias de Cypress
WORKDIR /workspace/cipress
RUN npm install

# Volver al directorio raíz
WORKDIR /workspace

# Comando por defecto: abrir terminal dentro del contenedor
CMD ["/bin/bash"]
