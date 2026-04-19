# Algorithm Foundations - Java

<p>
  <img alt="Java" src="https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=ffffff">
  <img alt="Algorithm" src="https://img.shields.io/badge/Algorithm-Study-1F6FEB">
  <img alt="Data Structure" src="https://img.shields.io/badge/Data%20Structure-Study-7C3AED">
</p>

アルゴリズムとデータ構造の基礎のうち、**走査・集計の基本 / 計算量 / 配列 / 文字列** を Java で学習するためのリポジトリ。  
コードを読み、実行し、出力を確認しながら、基礎概念を段階的に理解することを目的とする。  
各トピックごとにドキュメントを用意し、実装と対応づけながら見返せる形で整理している。

---

## 学習目的

このリポジトリでは、主に次の内容を目的として学習を行う。

- 走査・集計の基本的な考え方を理解する
- 計算量の初歩を理解する
- 配列の基本操作と特徴を理解する
- 文字列の基本操作と特徴を理解する
- Java のコードを読みながら、処理の流れを追えるようにする
- 実行結果や途中経過を見ながら、挙動を確認できるようにする

---

## 学習範囲

このリポジトリで扱うトピックは次の通り。

- 走査・集計の基本
- 計算量
- 配列
- 文字列

### 各トピックの位置づけ

- **走査・集計の基本**  
  配列や文字列を先頭から順に見ていく考え方、合計・件数・最大値・最小値などの基本的な集計処理を扱う

- **計算量**  
  O(1)、O(n)、O(n²) の代表例を通して、処理量の増え方の違いを確認する

- **配列**  
  要素の参照、更新、走査、集計など、配列を扱ううえでの基礎を確認する

- **文字列**  
  文字列の走査、文字数の取得、対象文字の件数集計、部分文字列、不変性などを確認する

---

## 学習の進め方

基本的な進め方は次の通り。

1. `docs/guide.md` を読み、このリポジトリ全体の構成と実行方法を把握する
2. `docs/topics/` 配下の対象トピックのドキュメントを読む
3. `App.java` から `--topic` を指定して実行する
4. 必要に応じて `--input`、`--trace`、`--target`、`--size` を使って挙動を変える
5. `runner/` と `algorithms/` 配下のコードを読み、コメントと出力を対応させながら理解する

---

## 前提環境

- Java 21
- VS Code などの Java を扱えるエディタ
- ビルドツールは使用しない
- `javac` と `java` でコンパイル・実行を行う

---

## 実行方法

### 1. コンパイル

#### bash / Git Bash

```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
```

#### PowerShell

```powershell
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $files
```

### 2. 実行

基本形は次の通り。

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic <topic>
```

例:

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic traversal
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic array
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic string
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic complexity
```

---

## 共通オプション

このリポジトリでは、共通で次のオプションを使う。

- `--topic`  
  実行するトピックを指定する

- `--input`  
  任意の入力値を直接指定する  
  例: `--input 5,3,8,1,4` / `--input algorithm`

- `--trace`  
  処理途中の流れを表示する

- `--target`  
  探索対象値など、別途指定したい値を渡す  
  必要なトピックのみで使用する

- `--size`  
  大きい入力データを自動生成したい場合のサイズ指定に使う  
  比較確認や差分把握で使用する

---

## 実行例

### 走査・集計の基本

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic traversal --trace
```

### 配列

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic array --input 5,3,8,1,4 --trace
```

### 文字列

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic string --input datastructure --target t --trace
```

### 計算量

```bash
java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic complexity --size 1000
```

---

## リポジトリ構成

```text
.
├─ src/
│  └─ io/
│     └─ github/
│        └─ seiya_matsuoka/
│           └─ algorithmfoundations/
│              ├─ App.java
│              ├─ RunnerOptions.java
│              ├─ runner/
│              └─ algorithms/
├─ docs/
│  ├─ guide.md
│  └─ topics/
│     ├─ traversal.md
│     ├─ array.md
│     ├─ string.md
│     └─ complexity.md
└─ README.md
```

### 各ディレクトリ・ファイルの役割

- `App.java`  
  共通エントリーポイント  
  引数を読み取り、対象の runner に振り分ける

- `RunnerOptions.java`  
  実行オプションを保持する

- `runner/`  
  入力の決定、実装呼び出し、出力表示を担当する

- `algorithms/`  
  学習対象となるアルゴリズムや基礎処理の実装本体を置く

- `docs/guide.md`  
  リポジトリ全体の案内と実行方法をまとめる

- `docs/topics/`  
  各トピックの個別ドキュメントを置く

---

## ドキュメント

- ガイド: [`docs/guide.md`](docs/guide.md)
- 走査・集計の基本: [`docs/topics/traversal.md`](docs/topics/traversal.md)
- 配列: [`docs/topics/array.md`](docs/topics/array.md)
- 文字列: [`docs/topics/string.md`](docs/topics/string.md)
- 計算量: [`docs/topics/complexity.md`](docs/topics/complexity.md)

---

## このリポジトリで確認できること

このリポジトリを一通り進めることで、次の状態を目指す。

- 走査・集計の基本処理をコードで追える
- 配列と文字列の基本操作を理解できる
- 計算量の初歩を説明できる
- Java の実装と出力を対応させながら読める
- `--topic` や各オプションを使って、自分で入力を変えながら確認できる
