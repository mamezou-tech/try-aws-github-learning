# ベースイメージはeclipse-temurin(旧OpenJDK)のJava17を使用
FROM docker.io/eclipse-temurin:17-jre-alpine

# 作業ディレクトリを/(root)にする
WORKDIR /
# Mavenのビルド成果物(sample-app.jar)をコンテナイメージにCOPY
COPY ./target/hello-app.jar ./
# Mavenのビルド成果物(libs以下を)をコンテナイメージにCOPY
COPY ./target/libs ./libs

# ExecutableJarをjavaコマンドで起動
CMD ["java", "-jar", "./hello-app.jar"]
