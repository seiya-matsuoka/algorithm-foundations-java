package io.github.seiya_matsuoka.algorithmfoundations.algorithms;

/**
 * 計算量の基本を確認するためのクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>同じ配列を題材にして、O(1)、O(n)、O(n^2) の代表的な処理を並べることで、 要素数が増えたときの増え方の違いをコードで確認できるようにしている。
 */
public class ComplexityBasics {
  /**
   * 真ん中の要素を1回だけ読み取る。
   *
   * <p>ここが O(1) の例。 配列サイズに関係なく、インデックスを決めて1件読むだけで終わる。
   */
  public ConstantSummary readMiddle(int[] values) {
    validateValues(values);

    // ここが O(1) の本体
    // 真ん中の位置を求めて、その位置の値を1回読む。
    int index = values.length / 2;
    int value = values[index];
    long operationCount = 1;

    return new ConstantSummary(index, value, operationCount);
  }

  /**
   * 配列全体を1回走査して合計値を求める。
   *
   * <p>ここが O(n) の例。 要素数に応じて、先頭から末尾まで1件ずつ見ていく。
   */
  public LinearSummary sumAll(int[] values) {
    validateValues(values);

    long sum = 0;
    long operationCount = 0;

    // ここが O(n) の本体
    // 各要素を順番に見ながら合計値を更新する。
    for (int value : values) {
      sum += value;
      operationCount++;
    }

    return new LinearSummary(sum, operationCount);
  }

  /**
   * 全ての組み合わせを二重ループで数える。
   *
   * <p>ここが O(n^2) の例。 外側のループごとに内側のループを最後まで回すため、 要素数が増えると処理回数が急激に増える。
   */
  public QuadraticSummary countAllPairs(int[] values) {
    validateValues(values);

    long pairCount = 0;
    long operationCount = 0;

    // ここが O(n^2) の本体
    // 全ての i と j の組み合わせに対して処理を行う。
    for (int i = 0; i < values.length; i++) {
      for (int j = 0; j < values.length; j++) {
        pairCount++;
        operationCount++;
      }
    }

    return new QuadraticSummary(pairCount, operationCount);
  }

  /**
   * 配列の妥当性を確認する。
   *
   * <p>これは計算量の学習テーマそのものではなく、 学習用コードが不正な状態で落ちないようにするための補助処理。
   */
  private void validateValues(int[] values) {
    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("配列は1件以上必要。");
    }
  }

  /**
   * O(1) の結果をまとめるクラス
   *
   * <p>実際に読んだ位置と値、操作回数をまとめて返すための構造。
   */
  public static class ConstantSummary {
    private final int index;
    private final int value;
    private final long operationCount;

    public ConstantSummary(int index, int value, long operationCount) {
      this.index = index;
      this.value = value;
      this.operationCount = operationCount;
    }

    public int getIndex() {
      return index;
    }

    public int getValue() {
      return value;
    }

    public long getOperationCount() {
      return operationCount;
    }
  }

  /**
   * O(n) の結果をまとめるクラス
   *
   * <p>合計値と走査回数をまとめて返すための構造。
   */
  public static class LinearSummary {
    private final long sum;
    private final long operationCount;

    public LinearSummary(long sum, long operationCount) {
      this.sum = sum;
      this.operationCount = operationCount;
    }

    public long getSum() {
      return sum;
    }

    public long getOperationCount() {
      return operationCount;
    }
  }

  /**
   * O(n^2) の結果をまとめるクラス
   *
   * <p>組み合わせ数と二重ループの実行回数をまとめて返すための構造。
   */
  public static class QuadraticSummary {
    private final long pairCount;
    private final long operationCount;

    public QuadraticSummary(long pairCount, long operationCount) {
      this.pairCount = pairCount;
      this.operationCount = operationCount;
    }

    public long getPairCount() {
      return pairCount;
    }

    public long getOperationCount() {
      return operationCount;
    }
  }
}
