package io.github.seiya_matsuoka.algorithmfoundations.runner;

import io.github.seiya_matsuoka.algorithmfoundations.RunnerOptions;
import io.github.seiya_matsuoka.algorithmfoundations.algorithms.TraversalBasics;
import java.util.Arrays;
import java.util.Random;

/**
 * 走査・集計の基本を実行する runner
 *
 * <p>このクラスは、入力データの決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>走査や集計そのものの本体ロジックは {@link TraversalBasics} に置いている。
 */
public class TraversalRunner implements TopicRunner {
  private final TraversalBasics traversalBasics = new TraversalBasics();

  @Override
  public void run(RunnerOptions options) {
    // 入力データは、--input → --size → 既定値 の順で決定する。
    int[] values = resolveValues(options);
    // target は「この値以上を数える」という集計条件として使う。
    int target = resolveTarget(options);

    System.out.println("=== 走査・集計の基本 ===");
    System.out.println("入力配列: " + formatArray(values));
    System.out.println("対象値(この値以上を数える): " + target);
    System.out.println();

    // trace 指定時は、1要素ずつ見ていく途中経過を表示する。
    if (options.isTrace()) {
      printTrace(values, target);
      System.out.println();
    }

    // ここから下が、学習テーマである「1回の走査で複数の集計を行う処理」の呼び出し部分
    long start = System.nanoTime();
    TraversalBasics.TraversalSummary summary = traversalBasics.summarize(values, target);
    long elapsed = System.nanoTime() - start;

    System.out.println("集計結果:");
    System.out.println("- 合計値: " + summary.getSum());
    System.out.println("- 最大値: " + summary.getMax());
    System.out.println("- 最小値: " + summary.getMin());
    System.out.printf("- 平均値: %.2f%n", summary.getAverage());
    System.out.println("- " + target + " 以上の件数: " + summary.getTargetCount());
    System.out.println("- 参考実行時間(ns): " + elapsed);
    System.out.println();
    System.out.println("補足:");
    System.out.println("- 走査は、配列の先頭から末尾まで順番に見ていく基本操作です。");
    System.out.println("- 合計・最大値・最小値・件数集計は、1回の走査でも同時に求められます。");
  }

  /**
   * 実行に使う配列を決定する。
   *
   * <p>このメソッドは学習テーマそのものではなく、デモを動かすための入力準備。
   */
  private int[] resolveValues(RunnerOptions options) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      return parseIntArray(options.getInput());
    }

    if (options.getSize() != null) {
      return createGeneratedValues(options.getSize());
    }

    return new int[] {5, 3, 8, 1, 4, 9, 2};
  }

  /**
   * カンマ区切り文字列を int 配列へ変換する。
   *
   * <p>これは入力準備のための補助処理
   */
  private int[] parseIntArray(String raw) {
    String[] tokens = raw.split(",");
    int[] values = new int[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      values[i] = Integer.parseInt(tokens[i].trim());
    }
    return values;
  }

  /**
   * 指定サイズのランダム配列を生成する。
   *
   * <p>大きめのデータで差を見たい場合のための補助機能。 アルゴリズムそのものではなく、比較用データ作成の役割。
   */
  private int[] createGeneratedValues(int size) {
    int actualSize = Math.max(1, size);
    int[] values = new int[actualSize];
    Random random = new Random(42L);
    for (int i = 0; i < actualSize; i++) {
      values[i] = random.nextInt(1000);
    }
    return values;
  }

  /** 集計条件として使う target 値を決定する。 */
  private int resolveTarget(RunnerOptions options) {
    if (options.getTarget() == null || options.getTarget().isBlank()) {
      return 5;
    }
    try {
      return Integer.parseInt(options.getTarget());
    } catch (NumberFormatException ex) {
      System.out.println("--target には整数を指定してください。今回は既定値 5 を使用します。");
      return 5;
    }
  }

  /**
   * 長い配列でもコンソール出力が見やすいように文字列化する。
   *
   * <p>これは表示用の補助処理。学習テーマそのものではない。
   */
  private String formatArray(int[] values) {
    if (values.length <= 20) {
      return Arrays.toString(values);
    }
    int[] head = Arrays.copyOfRange(values, 0, 10);
    int[] tail = Arrays.copyOfRange(values, values.length - 5, values.length);
    return Arrays.toString(head)
        + " ... "
        + Arrays.toString(tail)
        + " (length="
        + values.length
        + ")";
  }

  /**
   * trace 用に、走査中の状態変化を順番に表示する。
   *
   * <p>ここは学習理解を助けるための補助出力。 summarize と同じ考え方で、途中状態がどう変わるかを見えるようにしている。
   */
  private void printTrace(int[] values, int target) {
    int runningSum = 0;
    int currentMax = values[0];
    int currentMin = values[0];
    int currentCount = 0;

    System.out.println("途中経過:");
    for (int i = 0; i < values.length; i++) {
      int value = values[i];
      runningSum += value;
      if (value > currentMax) {
        currentMax = value;
      }
      if (value < currentMin) {
        currentMin = value;
      }
      if (value >= target) {
        currentCount++;
      }

      System.out.println(
          "- index="
              + i
              + ", value="
              + value
              + ", 合計="
              + runningSum
              + ", 最大="
              + currentMax
              + ", 最小="
              + currentMin
              + ", 件数="
              + currentCount);
    }
  }
}
