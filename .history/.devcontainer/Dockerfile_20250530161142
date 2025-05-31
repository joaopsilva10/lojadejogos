# Imagem base com Java 17 e ferramentas básicas
FROM openjdk:17-jdk-slim

# Usa bash como shell padrão
SHELL ["/bin/bash", "-c"]

# Argumentos padrão para criação do usuário
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Instala ferramentas úteis
RUN apt-get update && apt-get install -y \
    sudo \
    curl \
    git \
    unzip \
    gradle \
    bash \
    && rm -rf /var/lib/apt/lists/*
    
# Cria grupo/usuário
RUN groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME

# Adiciona o usuário `vscode` ao sudoers
RUN echo "$USERNAME ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

# Cria o diretório da aplicação e ajusta permissões
RUN mkdir -p /workspace && chown -R $USERNAME:$USERNAME /workspace

# Define diretório de trabalho
WORKDIR /workspace

# Define variáveis de ambiente
ENV JAVA_HOME=/usr/local/openjdk-17
ENV PATH=$JAVA_HOME/bin:$PATH

# Troca para o usuário vscode
USER $USERNAME

# Comando default
CMD ["sleep", "infinity"]