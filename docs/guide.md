# algorithm-foundations-java ガイド

## 1. このリポジトリで学ぶこと

このリポジトリでは、アルゴリズムとデータ構造の学習を始めるうえで土台となる内容を扱う。

対象トピックは次の4つ。

- 走査・集計の基本
- 配列
- 文字列
- 計算量

ここでの目的は、複雑なアルゴリズムに進む前に、**データを順番に見る・値を集計する・配列や文字列を基本操作する・処理量の増え方を理解する** という基礎を、Java のコードを読みながら確認すること。

---

## 2. 学習対象トピック一覧

### 2-1. 走査・集計の基本

- 配列を先頭から順番に見る
- 合計値、最大値、最小値、平均値を求める
- 指定値以上の件数を数える
- 1回の走査で複数の集計を同時に行う考え方を確認する

対応ドキュメント:

- `docs/topics/traversal.md`

### 2-2. 配列

- インデックスを使った読み取り
- 値の更新
- 途中への挿入
- 途中からの削除
- 配列が固定長であることの確認

対応ドキュメント:

- `docs/topics/array.md`

### 2-3. 文字列

- 長さの取得
- `charAt` を使った文字参照
- 1文字ずつの走査
- 特定文字の出現回数集計
- 部分文字列の切り出し
- `String` が不変であることの確認

対応ドキュメント:

- `docs/topics/string.md`

### 2-4. 計算量

- O(1)
- O(n)
- O(n^2)
- 入力サイズに対する処理量の増え方の比較
- 操作回数と参考実行時間の見方

対応ドキュメント:

- `docs/topics/complexity.md`

---

## 3. 推奨する学習順

このリポジトリ内では、次の順で進めるのがおすすめ。

1. 走査・集計の基本
2. 配列
3. 文字列
4. 計算量

### 理由

- 走査・集計の基本で「順番に見る」処理の考え方を先に押さえる
- 配列でインデックスアクセスと挿入・削除の重さを確認する
- 文字列で、配列に近い感覚で文字を扱えることと `String` の不変性を確認する
- 計算量で、ここまでの処理が入力サイズに応じてどう増えるかを整理する

---

## 4. ディレクトリ構成

```text
algorithm-foundations-java/
├─ src/
│  └─ io/
│     └─ github/
│        └─ seiya_matsuoka/
│           └─ algorithmfoundations/
│              ├─ App.java
│              ├─ RunnerOptions.java
│              ├─ runner/
│              │  ├─ TopicRunner.java
│              │  ├─ TraversalRunner.java
│              │  ├─ ArrayRunner.java
│              │  ├─ StringRunner.java
│              │  └─ ComplexityRunner.java
│              └─ algorithms/
│                 ├─ TraversalBasics.java
│                 ├─ ArrayBasics.java
│                 ├─ StringBasics.java
│                 └─ ComplexityBasics.java
├─ docs/
│  ├─ guide.md
│  └─ topics/
│     ├─ traversal.md
│     ├─ array.md
│     ├─ string.md
│     └─ complexity.md
└─ .gitignore
```

### 4-1. 各ファイル・ディレクトリの役割

#### `App.java`

共通エントリーポイント。  
コマンドライン引数を受け取り、指定された topic に応じて対応する runner に処理を振り分ける。

#### `RunnerOptions.java`

実行時オプション保持用クラス。  
`--topic`、`--input`、`--trace`、`--target`、`--size` の値をまとめて runner に渡す。

#### `runner/`

実行用クラス群。  
入力データの決定、trace 表示、表示用メッセージの整形など、学習用デモとして動かすための周辺処理を担当する。

#### `algorithms/`

学習テーマのコアとなる実装本体。  
実際の走査、集計、配列操作、文字列操作、計算量比較の処理はここに置く。

#### `docs/topics/`

各学習トピックの説明ドキュメント。  
コードと対応づけながら読むことを前提とする。

---

## 5. 実行方法

このリポジトリは Gradle などのビルドツールを使わず、素の Java でコンパイル・実行する。

### 5-1. 前提

- Java 21 を使用
- VS Code では、フォルダを開いてターミナルからコンパイル・実行すればよい

### 5-2. bash / zsh でのコンパイル

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

### 5-3. PowerShell でのコンパイル

```powershell
$files = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

### 5-4. 実行コマンドの基本形

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic <topic> [options]
```

---

## 6. 共通オプション

このリポジトリでは、次の5つの共通オプションを使う。

### 6-1. `--topic`

実行する学習トピックを指定する。

指定できる値:

- `traversal`
- `array`
- `string`
- `complexity`

### 6-2. `--input`

入力値を直接指定する。

例:

- `--input 5,3,8,1,4`
- `--input algorithm`

### 6-3. `--trace`

処理途中の流れを表示する。

使いどころ:

- 走査中の変数更新確認
- 配列操作の途中状態確認
- 文字列を1文字ずつ見る流れの確認
- 計算量トピックでの処理量イメージ確認

### 6-4. `--target`

探索や集計で使う対象値を指定する。

使いどころ:

- 走査・集計で「何以上を数えるか」を指定する
- 文字列で「どの文字を数えるか」を指定する

不要なトピックでは、指定されていても使わずに無視する。

### 6-5. `--size`

大きい入力データを生成したい場合のサイズを指定する。

使いどころ:

- 走査・集計で大きい配列を作る
- 配列操作で大きめ入力を使う
- 文字列で長いランダム文字列を生成する
- 計算量で入力サイズ差を確認する

---

## 7. 入力データの決まり方

各トピックで使う入力データは、次の優先順位で決定する。

1. `--input` が指定されていればそれを使う
2. `--input` がなく、`--size` が指定されていれば、そのサイズで入力を生成する
3. どちらもなければ、学習用の小さいデフォルト値を使う

### 7-1. この順にしている理由

- まずは自分で試したい入力を最優先にできる
- 入力サイズだけ変えて差を見たい場合にも対応できる
- 何も指定しなくてもすぐ実行できる

---

## 8. topic ごとの実行例

### 8-1. 走査・集計の基本

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic traversal --trace
```

#### target 指定

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic traversal --input 5,3,8,1,4,9,2 --target 5 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic traversal --size 10000 --target 500
```

### 8-2. 配列

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic array --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic array --input 10,20,30,40,50 --target 777 --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic array --size 10000 --target 777
```

### 8-3. 文字列

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic string --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic string --input datastructure --target t --trace
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic string --size 10000 --target a
```

### 8-4. 計算量

#### 基本実行

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic complexity --trace
```

#### 入力指定

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic complexity --input 1,2,3,4,5,6
```

#### 大きい入力

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic complexity --size 1000
```

---

## 9. 学習時の見方

### 9-1. まず見るべき場所

まずは次の順で見るのがおすすめ。

1. `docs/topics/*.md` で概要と処理の流れを読む
2. `runner/` で入力決定や表示の流れを確認する
3. `algorithms/` でコアとなる処理を読む
4. 実際に `--trace` 付きで実行して出力を見る

### 9-2. 特に見るべきポイント

- どこが学習テーマの本体処理か
- どこが入力準備や表示のための補助処理か
- 1回の走査で何を同時に更新しているか
- 配列や文字列で、何が O(1) で何が O(n) になるか
- 計算量トピックで、ループ構造がどう処理量に影響するか

### 9-3. 実行時間の見方

実行時間は参考値として表示することがあるが、学習の中心は次の順。

1. 理論計算量
2. 操作回数やループ回数
3. 参考実行時間

特に計算量トピックでは、秒数そのものより「入力サイズが増えたときにどう増えるか」を見る。

---

## 10. このリポジトリを終えた時点で目指す状態

このリポジトリを終えた時点で、次の状態を目指す。

- 走査・集計の基本が説明できる
- 配列の読み取り、更新、挿入、削除の違いが説明できる
- 文字列を1文字ずつ見る処理と `String` の不変性が説明できる
- O(1)、O(n)、O(n^2) の違いが説明できる
- コードを読んだときに、学習本体処理と補助処理を見分けられる
