package io.github.seiya_matsuoka.algorithmfoundations.runner;

import io.github.seiya_matsuoka.algorithmfoundations.RunnerOptions;
import io.github.seiya_matsuoka.algorithmfoundations.algorithms.ArrayBasics;
import java.util.Arrays;
import java.util.Random;

/**
 * 配列の基本を実行する runner
 *
 * <p>このクラスは、配列の読み取り・更新・挿入・削除という学習テーマを 実際に試せるように、入力準備、実行手順の組み立て、結果表示を担当する。
 *
 * <p>配列操作そのものの本体ロジックは {@link ArrayBasics} に置いている。
 */
public class ArrayRunner implements TopicRunner {
  private final ArrayBasics arrayBasics = new ArrayBasics();

  @Override
  public void run(RunnerOptions options) {
    // 入力配列は、--input → --size → 既定値 の順で決定する。
    int[] values = resolveValues(options);

    // 今回のデモでは中央付近の位置を使って、各基本操作を見やすく実行する。
    int readIndex = values.length / 2;
    int updateIndex = values.length / 2;
    int insertIndex = values.length / 2;
    int removeIndex = values.length / 2;
    int updateValue = resolveTarget(options, 99);
    int insertValue = resolveTarget(options, 77);

    System.out.println("=== 配列の基本 ===");
    System.out.println("元の配列: " + formatArray(values));
    System.out.println();

    // ここから下が、学習テーマである配列操作の本体呼び出し部分
    int readValue = arrayBasics.readAt(values, readIndex);
    int[] updated = arrayBasics.updateAt(values, updateIndex, updateValue);
    int[] inserted = arrayBasics.insertAt(values, insertIndex, insertValue);
    int[] removed = arrayBasics.removeAt(values, removeIndex);

    System.out.println("実行結果:");
    System.out.println("- index " + readIndex + " の値を読む: " + readValue);
    System.out.println(
        "- index " + updateIndex + " を " + updateValue + " に更新した結果: " + formatArray(updated));
    System.out.println(
        "- index " + insertIndex + " に " + insertValue + " を挿入した結果: " + formatArray(inserted));
    System.out.println("- index " + removeIndex + " を削除した結果: " + formatArray(removed));
    System.out.println();

    // trace 指定時は、内部でどのような考え方で処理しているかを補足表示する。
    if (options.isTrace()) {
      printTrace(
          values, readIndex, updateIndex, updateValue, insertIndex, insertValue, removeIndex);
      System.out.println();
    }

    // 参考として、挿入と削除の処理時間を簡単に測る。
    // ただし、今回は実行時間よりも「要素をずらす必要があること」の理解を優先する。
    long start = System.nanoTime();
    arrayBasics.insertAt(values, insertIndex, insertValue);
    long insertElapsed = System.nanoTime() - start;

    long removeStart = System.nanoTime();
    arrayBasics.removeAt(values, removeIndex);
    long removeElapsed = System.nanoTime() - removeStart;

    System.out.println("補足:");
    System.out.println("- 配列はインデックスを使った読み取りが得意です。");
    System.out.println("- ただし途中への挿入や削除では、後ろの要素をずらす必要があります。");
    System.out.println("- 参考実行時間(ns): 挿入=" + insertElapsed + ", 削除=" + removeElapsed);
  }

  /**
   * 実行に使う配列を決定する。
   *
   * <p>ここはデモ用データの準備処理。学習テーマそのものではない。
   */
  private int[] resolveValues(RunnerOptions options) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      return parseIntArray(options.getInput());
    }

    if (options.getSize() != null) {
      return createGeneratedValues(options.getSize());
    }

    return new int[] {10, 20, 30, 40, 50};
  }

  /** カンマ区切り文字列を int 配列へ変換する。 */
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
   * <p>大きい配列でも配列操作を試せるようにするための補助処理
   */
  private int[] createGeneratedValues(int size) {
    int actualSize = Math.max(1, size);
    int[] values = new int[actualSize];
    Random random = new Random(24L);
    for (int i = 0; i < actualSize; i++) {
      values[i] = random.nextInt(500);
    }
    return values;
  }

  /**
   * 更新値や挿入値として使う値を決定する。
   *
   * <p>この runner では、target を「更新時・挿入時に使う値」として再利用している。
   */
  private int resolveTarget(RunnerOptions options, int defaultValue) {
    if (options.getTarget() == null || options.getTarget().isBlank()) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(options.getTarget());
    } catch (NumberFormatException ex) {
      System.out.println("--target には整数を指定してください。今回は既定値 " + defaultValue + " を使用します。");
      return defaultValue;
    }
  }

  /** 長い配列でも出力を読みやすくするための表示用補助メソッド */
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
   * trace 用に、各操作の考え方を日本語で補足表示する。
   *
   * <p>ここは学習理解を助けるための説明用処理。配列操作の本体ではない。
   */
  private void printTrace(
      int[] values,
      int readIndex,
      int updateIndex,
      int updateValue,
      int insertIndex,
      int insertValue,
      int removeIndex) {
    System.out.println("途中経過の説明:");
    System.out.println("- 読み取り: values[" + readIndex + "] に直接アクセスします。");
    System.out.println(
        "- 更新: 元の配列をコピーしたあと、index " + updateIndex + " の値を " + updateValue + " に置き換えます。");
    System.out.println("- 挿入: index " + insertIndex + " 以降の要素を右へ1つずつずらす必要があります。");
    System.out.println("- 削除: index " + removeIndex + " より後ろの要素を左へ詰める必要があります。");
    System.out.println("- 元の配列は固定長のため、挿入後と削除後は別の配列として扱います。");
    System.out.println("- 元の配列(再確認): " + formatArray(values));
  }
}
