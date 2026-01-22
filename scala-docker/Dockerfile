FROM eclipse-temurin:17-jdk

# Install system + X11 dependencies
RUN apt-get update && apt-get install -y \
    curl \
    gnupg \
    x11-apps \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libfreetype6 \
    libfontconfig1 \
    && rm -rf /var/lib/apt/lists/*

# Install sbt
RUN curl -fsSL https://repo.scala-sbt.org/scalasbt/debian/sbt-1.9.9.deb -o sbt.deb \
    && apt-get update \
    && apt-get install -y ./sbt.deb \
    && rm sbt.deb

# Application directory
WORKDIR /app
COPY . .

# GUI environment
ENV DISPLAY=host.docker.internal:0
ENV JAVA_OPTS="-Djava.awt.headless=false"
ENV _JAVA_OPTIONS="-Dsun.java2d.xrender=false"
ENV SBT_OPTS="-Dsbt.color=false"

# Run the game
CMD ["sbt", "run"]
