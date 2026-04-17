package io.github.seiya_matsuoka.algorithmfoundations.algorithms;

/**
 * 走査・集計の基本処理をまとめたクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>「配列を先頭から末尾まで1回ずつ見ながら、複数の情報を同時に集計する」 という考え方をコードで再現している。
 */
public class TraversalBasics {
  /**
   * 配列を1回走査して、複数の集計結果をまとめて返す。
   *
   * <p>ここが学習テーマのコア部分。 合計・最大値・最小値・平均値の元になる値・条件に合う件数を、 1回のループの中で同時に更新している。
   */
  public TraversalSummary summarize(int[] values, int target) {
    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("配列は1件以上必要です。");
    }

    // 走査しながら更新していく集計用の変数
    int sum = 0;
    int max = values[0];
    int min = values[0];
    int targetCount = 0;

    // ここが走査処理の本体
    // 各要素を1回ずつ見ながら、必要な集計結果を更新する。
    for (int value : values) {
      // 合計値の更新
      sum += value;

      // 最大値の更新
      if (value > max) {
        max = value;
      }

      // 最小値の更新
      if (value < min) {
        min = value;
      }

      // target 以上の件数を数える条件判定
      if (value >= target) {
        targetCount++;
      }
    }

    // 平均値は、走査後に合計値と要素数から計算する。
    double average = (double) sum / values.length;
    return new TraversalSummary(sum, max, min, average, targetCount);
  }

  /**
   * 走査・集計結果をまとめるクラス
   *
   * <p>複数の集計結果を1つの戻り値として扱うために用意している。
   *
   * <p>これは学習テーマの本体というより、結果を扱いやすくするための補助的な構造となる
   */
  public static class TraversalSummary {
    private final int sum;
    private final int max;
    private final int min;
    private final double average;
    private final int targetCount;

    public TraversalSummary(int sum, int max, int min, double average, int targetCount) {
      this.sum = sum;
      this.max = max;
      this.min = min;
      this.average = average;
      this.targetCount = targetCount;
    }

    public int getSum() {
      return sum;
    }

    public int getMax() {
      return max;
    }

    public int getMin() {
      return min;
    }

    public double getAverage() {
      return average;
    }

    public int getTargetCount() {
      return targetCount;
    }
  }
}
