
# JSLT Sample Application

## 概要

このアプリケーションは、Apache Camel を使用して外部の API からデータを取得し、JSLT (JSONスキーマ変換言語) を使用してデータを変換するサンプルコードです。以下の手順でデータを処理します：

1. **Timerトリガー**: 定期的に処理を開始します。
2. **外部API呼び出し**: ランダムなユーザーデータを取得します。
3. **データ変換 (JSLT)**: JSLT を用いてデータを新しい形式に変換します。
4. **ログ出力**: 変換前と変換後のデータをログに記録します。

## 必要要件

- Java 11以上
- camel-jbang

## ファイル構成

### jslt_sample.java

このファイルは、Apache Camel ルートを定義します。

#### 主な処理の流れ

1. **Timer:**

   ```java
   from("timer:template?period=1000&repeatCount=1")
   ```

   この部分は、1秒間隔で1回だけトリガーするタイマーを設定しています。

2. **外部API呼び出し:**

   ```java
   .to("https://random-data-api.com/api/v2/users?size=1")
   ```

   ランダムなユーザー情報を取得するAPIを呼び出します。

3. **JSLT変換:**

   ```java
   .to("jslt:file://mapping.json")
   ```

   データを `mapping.json` ファイルのJSLTスキーマに基づいて変換します。

4. **ログ出力:**

   ```java
   .log("Before JSLT: ${body}")
   .log("After JSLT: ${body}")
   ```

   変換前後のデータをログに出力します。

### mapping.json

このファイルには、取得したJSONデータを変換するためのJSLTスキーマが記載されています。

#### 変換内容

- `fullName`: `first_name` と `last_name` を結合してフルネームを作成。
- `fullAddress`: `address` オブジェクト内の各要素を結合して完全な住所を生成。

```json
{
    "fullName": .first_name + " " + .last_name,
    "fullAddress": .address.street_address + ", " + .address.street_name + ", " + .address.city + ", " + .address.state + ", " + .address.country
}
```

## 実行方法

camel-jbang（camel CLI）で以下を実行します

```bash
camel run jslt_sample.java
```

## サンプル出力

```log
Before JSLT: {"id":3919,"uid":"275a941b-9466-4390-b70c-3461b4f527d3","password":"OuWLo9kYHq","first_name":"Gonzalo","last_name":"Hills","username":"gonzalo.hills","email":"gonzalo.hills@email.com","avatar":"https://robohash.org/repudiandaeautemillum.png?size=300x300\u0026set=set1","gender":"Genderfluid","phone_number":"+48 765.101.4160 x816","social_insurance_number":"924520448","date_of_birth":"1971-05-02","employment":{"title":"Global Sales Executive","key_skill":"Confidence"},"address":{"city":"North Wilmerhaven","street_name":"Melaine Meadow","street_address":"95507 Nada Glens","zip_code":"72324-8304","state":"Kentucky","country":"United States","coordinates":{"lat":-28.176588228310273,"lng":105.31580321791637}},"credit_card":{"cc_number":"4100436873073"},"subscription":{"plan":"Free Trial","status":"Blocked","payment_method":"Credit card","term":"Annual"}}

After JSLT: {"fullName":"Gonzalo Hills","fullAddress":"95507 Nada Glens, Melaine Meadow, North Wilmerhaven, Kentucky, United States"}
```

## 参考リンク

- [JSLT Component](https://camel.apache.org/components/4.8.x/jslt-component.html)
- [JSLT tutorial](https://github.com/schibsted/jslt/blob/master/tutorial.md)
