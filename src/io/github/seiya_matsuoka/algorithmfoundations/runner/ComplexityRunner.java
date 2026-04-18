package io.github.seiya_matsuoka.algorithmfoundations.runner;

import io.github.seiya_matsuoka.algorithmfoundations.RunnerOptions;
import io.github.seiya_matsuoka.algorithmfoundations.algorithms.ComplexityBasics;
import java.util.Arrays;

/**
 * 計算量の基本を実行する runner
 *
 * <p>このクラスは、入力データの決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>計算量の比較そのものの本体ロジックは {@link ComplexityBasics} に置いている。
 */
public class ComplexityRunner implements TopicRunner {
  /**
   * O(n^2) の例で扱う最大件数
   *
   * <p>サイズが大きすぎると実行時間が急激に増えるため、 学習用として無理のない上限を設ける。
   */
  private static final int QUADRATIC_LIMIT = 2_000;

  private final ComplexityBasics complexityBasics = new ComplexityBasics();

  @Override
  public void run(RunnerOptions options) {
    // 入力データは、--input → --size → 既定値 の順で決定する。
    int[] values = resolveValues(options);
    int[] quadraticValues = resolveQuadraticValues(values);

    System.out.println("=== 計算量の基本 ===");
    System.out.println("入力配列: " + formatArray(values));
    System.out.println("要素数: " + values.length);
    System.out.println();

    if (options.getTarget() != null && !options.getTarget().isBlank()) {
      System.out.println("補足: complexity では --target を使用しないため、今回は無視する。");
      System.out.println();
    }

    // trace 指定時は、計算量ごとの処理の違いを途中経過で表示する。
    if (options.isTrace()) {
      printTrace(values, quadraticValues);
      System.out.println();
    }

    // O(1) の例: 真ん中の要素へ直接アクセスする。
    long constantStart = System.nanoTime();
    ComplexityBasics.ConstantSummary constantSummary = complexityBasics.readMiddle(values);
    long constantElapsed = System.nanoTime() - constantStart;

    // O(n) の例: 配列全体を1回走査して合計を求める。
    long linearStart = System.nanoTime();
    ComplexityBasics.LinearSummary linearSummary = complexityBasics.sumAll(values);
    long linearElapsed = System.nanoTime() - linearStart;

    // O(n^2) の例: 全ての組み合わせを作る二重ループを実行する。
    long quadraticStart = System.nanoTime();
    ComplexityBasics.QuadraticSummary quadraticSummary =
        complexityBasics.countAllPairs(quadraticValues);
    long quadraticElapsed = System.nanoTime() - quadraticStart;

    System.out.println("結果:");
    System.out.println("- O(1) の例: 真ん中の要素を読む");
    System.out.println("  - 対象 index: " + constantSummary.getIndex());
    System.out.println("  - 読み取った値: " + constantSummary.getValue());
    System.out.println("  - 操作回数: " + constantSummary.getOperationCount());
    System.out.println("  - 参考実行時間(ns): " + constantElapsed);
    System.out.println();
    System.out.println("- O(n) の例: 配列全体の合計を求める");
    System.out.println("  - 合計値: " + linearSummary.getSum());
    System.out.println("  - 走査回数: " + linearSummary.getOperationCount());
    System.out.println("  - 参考実行時間(ns): " + linearElapsed);
    System.out.println();
    System.out.println("- O(n^2) の例: 全ての組み合わせを数える");
    System.out.println("  - 比較に使った要素数: " + quadraticValues.length);
    System.out.println("  - 組み合わせ数: " + quadraticSummary.getPairCount());
    System.out.println("  - 二重ループの内側実行回数: " + quadraticSummary.getOperationCount());
    System.out.println("  - 参考実行時間(ns): " + quadraticElapsed);
    if (values.length > quadraticValues.length) {
      System.out.println("  - 補足: 実行負荷を抑えるため、O(n^2) は先頭 " + quadraticValues.length + " 件で比較した。");
    }
    System.out.println();
    System.out.println("補足:");
    System.out.println("- O(1) は要素数が増えても操作回数がほとんど変わらない例。");
    System.out.println("- O(n) は要素数に比例して処理回数が増える例。");
    System.out.println("- O(n^2) は要素数が増えると急激に処理回数が増える例。");
    System.out.println("- 実行時間は参考値。学習では理論計算量と操作回数を優先して見る。\n");
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
      return createSequentialValues(options.getSize());
    }

    return new int[] {1, 2, 3, 4, 5};
  }

  /**
   * カンマ区切り文字列を int 配列へ変換する。
   *
   * <p>これは入力準備のための補助処理。
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
   * 指定サイズの連番配列を生成する。
   *
   * <p>計算量比較では、入力値そのものより件数が重要になるため、 ここでは分かりやすく 1 から始まる連番を使う。
   */
  private int[] createSequentialValues(int size) {
    int actualSize = Math.max(1, size);
    int[] values = new int[actualSize];
    for (int i = 0; i < actualSize; i++) {
      values[i] = i + 1;
    }
    return values;
  }

  /**
   * O(n^2) の比較に使う配列を決定する。
   *
   * <p>二重ループはサイズが大きいと急激に重くなるため、 学習用の上限を超える場合は先頭部分だけを使う。
   */
  private int[] resolveQuadraticValues(int[] values) {
    if (values.length <= QUADRATIC_LIMIT) {
      return values;
    }
    return Arrays.copyOf(values, QUADRATIC_LIMIT);
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
   * trace 用に、計算量ごとの処理の違いを表示する。
   *
   * <p>ここは学習理解を助けるための補助出力。
   *
   * <p>O(1)、O(n)、O(n^2) でループ回数がどう変わるかを見えるようにする。
   */
  private void printTrace(int[] values, int[] quadraticValues) {
    printConstantTrace(values);
    System.out.println();
    printLinearTrace(values);
    System.out.println();
    printQuadraticTrace(quadraticValues, values.length > quadraticValues.length);
  }

  /** O(1) の途中経過を表示する。 */
  private void printConstantTrace(int[] values) {
    int index = values.length / 2;
    System.out.println("途中経過: O(1) の例");
    System.out.println("- 要素数に関係なく、真ん中の index を計算して1回読むだけ。");
    System.out.println("- index=" + index + ", value=" + values[index]);
  }

  /** O(n) の途中経過を表示する。 */
  private void printLinearTrace(int[] values) {
    long sum = 0;
    System.out.println("途中経過: O(n) の例");
    for (int i = 0; i < values.length; i++) {
      sum += values[i];
      System.out.println(
          "- index=" + i + ", value=" + values[i] + ", 合計=" + sum + ", 走査回数=" + (i + 1));
    }
  }

  /**
   * O(n^2) の途中経過を表示する。
   *
   * <p>二重ループは出力量が多くなりやすいため、trace では小さい入力だけ詳細表示する。
   */
  private void printQuadraticTrace(int[] values, boolean truncated) {
    System.out.println("途中経過: O(n^2) の例");

    if (truncated) {
      System.out.println("- 入力全体では大きすぎるため、先頭 " + values.length + " 件に絞って表示する。");
    }

    if (values.length > 6) {
      System.out.println("- 出力が長くなりすぎるため、詳細 trace は 6 件以下のときだけ表示する。");
      System.out.println(
          "- 今回の二重ループ実行回数は理論上 " + ((long) values.length * values.length) + " 回となる。\n");
      return;
    }

    long operationCount = 0;
    for (int i = 0; i < values.length; i++) {
      for (int j = 0; j < values.length; j++) {
        operationCount++;
        System.out.println(
            "- i="
                + i
                + ", j="
                + j
                + ", 左="
                + values[i]
                + ", 右="
                + values[j]
                + ", 実行回数="
                + operationCount);
      }
    }
  }
}
