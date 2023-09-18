# AWSとGitHubを使ってみよう勉強会 資料
- [第1回 キックオフ](docs/AWSとGitHub勉強会_1st_キックオフ.pdf)
- [第2回 GitHub CodespacesとHelidonの利用](docs/AWSとGitHub勉強会_2nd_CodespacesとHelidon.pdf)
- [第3回 GitHub Actionsを使ったCI環境の構築](docs/AWSとGitHub勉強会_3rd_GitHub_CI.pdf)
- [第4回 GitHub Actionsを使ったCD環境の構築](docs/AWSとGitHub勉強会_4th_GitHub_CD.pdf)
- [第5回 AWS EC2環境の構築](docs/AWSとGitHub勉強会_5th_EC2.pdf)
- [第6回 AWS ECS Fargate環境の構築](docs/AWSとGitHub勉強会_6th_Fargate.pdf)


# 勉強会 補足情報
## 2回目課題(GitHub Actionsでビルドでしてjarをデプロイする)に関する情報
### sampleアプリのビルドと起動
```shell
# ビルド
mvn clean package
# 起動
java -jar target/hello-app.jar
# REST APIへのリクエスト
curl -X GET localhost:7001/api/hello
```
### pom.xmlの追加設定のsnippet
```xml
	<distributionManagement>
		<repository>
			<id>github</id>
			<name>GitHub Packages</name>
			<url>https://maven.pkg.github.com/@your_account@/@your_repository@</url>
		</repository>
	</distributionManagement>
```
※snippetの右上のアイコンをクリックするとコピーできる


## 3回目課題(GitHub Actionsでコンテナイメージをビルドでしてデプロイする)に関する情報
### Dockerfileの確認（Dockerfileを作ってから行う）
```shell
# jarのビルド
mvn clean package
# コンテナイメージのビルド
docker build -t hello-app .
# ローカルリポジトリに登録されていることの確認(hello-appがあること)
docker images hello-app
# コンテナの実行
docker run -p 7001:7001 --name hello-app --rm hello-app
# REST APIへのリクエスト(別のターミナルから)
curl -X GET localhost:7001/api/hello
```

## 4回目課題のEC2インスタンスの環境構築＆Dcokerコマンド
### SSH接続
- pemファイルをダウンロードしたディレクトリに移動
- AWSコンソールの「接続」で確認したコマンドを実行（4回目資料のp.32）
- EC2インスタンスに割り当てられるパブリックIPアドレスはEC2の起動ごとに変わる
- なので、EC2を再起動した場合はAWSコンソールの「接続」で再度接続に必要なコマンドを確認し、それを利用すること
- なお、Windowsは標準でSSHクライアントがインストールされていないため、適当なツールをインストールする必要がある

### タイムゾーンとロケールの変更
```shell
# タイムゾーンの変更
sudo timedatectl set-timezone Asia/Tokyo
# 変更後の確認
timedatectl status
# ロケールの変更
sudo localectl set-locale LANG=ja_JP.UTF-8
# keymapの変更
sudo localectl set-keymap jp106
# 変更後の確認
localectl status
```
> 実行結果は4回目資料のp.34で確認

### Dockerのインストール
```shell
# インストールするパッケージの最新化
sudo yum update -y
# Dockerのインストール
sudo amazon-linux-extras install -y docker
# Dockerサービスの開始
sudo systemctl start docker
# Dockerサービスの起動確認
sudo systemctl status docker
# Dockerの自動起動の登録
sudo systemctl enable docker
```
> 実行結果は4回目資料のp.35-37で確認

### コンテナの起動
```shell
# コンテナの起動
sudo docker run -d --rm --name hello-app -p 7001:7001 ghcr.io/[自分のGitHubユーザ名]/hello-app:latest
# 起動ログの確認
sudo docker logs hello-app
```
> 実行結果は4回目資料のp.38で確認

### REST APIの呼び出し
```shell
# コンテナの起動
curl -X GET http://[EC2インスタンスのパブリックIP]:7001/api/hello
```
> 実行結果は4回目資料のp.39で確認  
> なお、EC2インスタンスに割り当てられるパブリックIPアドレスはEC2の起動ごとに変わるので、EC2を再起動した場合はAWSコンソールから再度パブリックIPアドレスを確認すること(IPアドレスの確認方法は4回目資料のp.30を参照)


## 5回目課題のビルドしたイメージをEC2からpullして動作を確認するDockerコマンド
### SSH接続
- AWSコンソールの「接続」で確認したコマンドを実行（4回目資料のp.32）

### デフォルト設定でのコンテナ起動
```shell
# 最新のコンテナイメージの取得
sudo docker pull ghcr.io/[自分のGitHubユーザ名]/hello-app:latest
# コンテナの起動
sudo docker run -d --rm --name hello-app -p 7001:7001 ghcr.io/[自分のGitHubユーザ名]/hello-app:latest
# 起動ログの確認
sudo docker logs hello-app
# レスポンスの確認
curl localhost:7001/api/hello
> Helllo
```
### 設定を変更してコンテナ起動
```shell
# コンテナの起動(-eオプションを追加)
sudo docker run -d --rm --name hello-app -p 7001:7001 -e CONFIG_VAL=changed ghcr.io/[自分のGitHubユーザ名]/hello-app:latest
# 起動ログの確認
sudo docker logs hello-app
# レスポンスの確認
curl localhost:7001/api/hello
> changed
```

