package io.github.seiya_matsuoka.algorithmfoundations.runner;

import io.github.seiya_matsuoka.algorithmfoundations.RunnerOptions;
import io.github.seiya_matsuoka.algorithmfoundations.algorithms.StringBasics;
import java.util.Random;

/**
 * 文字列の基本を実行する runner
 *
 * <p>このクラスは、入力文字列の決定、trace 表示、結果表示など、 「実際に学習用デモとして動かすための周辺処理」を担当する。
 *
 * <p>文字列の走査や基本操作そのものの本体ロジックは {@link StringBasics} に置いている。
 */
public class StringRunner implements TopicRunner {
  private final StringBasics stringBasics = new StringBasics();

  @Override
  public void run(RunnerOptions options) {
    // 入力文字列は、--input → --size → 既定値 の順で決定する。
    String text = resolveText(options);
    // target は「数えたい文字」として使う。
    char target = resolveTarget(options);

    System.out.println("=== 文字列の基本 ===");
    System.out.println("入力文字列: " + text);
    System.out.println("対象文字: " + target);
    System.out.println();

    // trace 指定時は、1文字ずつ見ていく途中経過を表示する。
    if (options.isTrace()) {
      printTrace(text, target);
      System.out.println();
    }

    // ここから下が、学習テーマである「文字列を順番に見ながら情報を集計する処理」の呼び出し部分
    long start = System.nanoTime();
    StringBasics.StringSummary summary = stringBasics.summarize(text, target);
    long elapsed = System.nanoTime() - start;

    // 文字列の基本操作も合わせて確認する。
    String prefix = stringBasics.extractPrefix(text, 3);
    String middle = stringBasics.extractMiddle(text);
    String replaced = stringBasics.replaceAt(text, 0, Character.toUpperCase(target));
    String reversed = stringBasics.reverse(text);

    System.out.println("分析結果:");
    System.out.println("- 文字数: " + summary.getLength());
    System.out.println("- 先頭文字: " + summary.getFirstChar());
    System.out.println("- 末尾文字: " + summary.getLastChar());
    System.out.println("- \"" + target + "\" の出現回数: " + summary.getTargetCount());
    if (summary.getFirstTargetIndex() >= 0) {
      System.out.println("- \"" + target + "\" が最初に出現する位置: " + summary.getFirstTargetIndex());
    } else {
      System.out.println("- \"" + target + "\" は文字列内に存在しない");
    }
    System.out.println("- 先頭3文字: " + prefix);
    System.out.println("- 中央付近の部分文字列: " + middle);
    System.out.println("- 先頭1文字を置き換えた新しい文字列: " + replaced);
    System.out.println("- 反転した文字列: " + reversed);
    System.out.println("- 元の文字列(再確認): " + text);
    System.out.println("- 参考実行時間(ns): " + elapsed);
    System.out.println();
    System.out.println("補足:");
    System.out.println("- 文字列は文字の並びとして扱えるため、配列に近い感覚で順番に見ることができる。");
    System.out.println("- ただし String は不変のため、文字を変更する場合は新しい文字列を作る必要がある。");
    System.out.println("- 長さ取得、文字参照、部分文字列、走査は文字列の基本操作。");
  }

  /**
   * 実行に使う文字列を決定する。
   *
   * <p>このメソッドは学習テーマそのものではなく、デモを動かすための入力準備。
   */
  private String resolveText(RunnerOptions options) {
    if (options.getInput() != null && !options.getInput().isBlank()) {
      return options.getInput();
    }

    if (options.getSize() != null) {
      return createGeneratedText(options.getSize());
    }

    return "algorithm";
  }

  /**
   * 指定サイズのランダム文字列を生成する。
   *
   * <p>大きめの入力で差を見たい場合のための補助機能。学習テーマそのものではなく、比較用データ作成の役割。
   */
  private String createGeneratedText(int size) {
    int actualSize = Math.max(1, size);
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    Random random = new Random(42L);
    StringBuilder builder = new StringBuilder(actualSize);

    for (int i = 0; i < actualSize; i++) {
      builder.append(alphabet.charAt(random.nextInt(alphabet.length())));
    }

    return builder.toString();
  }

  /**
   * 集計対象として使う文字を決定する。
   *
   * <p>文字列トピックでは 1 文字単位の確認を中心にするため、 target が複数文字の場合は先頭 1 文字だけを利用する。
   */
  private char resolveTarget(RunnerOptions options) {
    if (options.getTarget() == null || options.getTarget().isBlank()) {
      return 'a';
    }

    if (options.getTarget().length() > 1) {
      System.out.println("--target は先頭1文字だけを使用する。指定値: " + options.getTarget());
    }
    return options.getTarget().charAt(0);
  }

  /**
   * trace 用に、文字列を先頭から順に見ていく途中経過を表示する。
   *
   * <p>ここは学習理解を助けるための補助出力。
   *
   * <p>summarize と同じ考え方で、何文字目を見ているかと件数の更新を見えるようにする。
   */
  private void printTrace(String text, char target) {
    int currentCount = 0;

    System.out.println("途中経過:");
    for (int i = 0; i < text.length(); i++) {
      char current = text.charAt(i);

      if (current == target) {
        currentCount++;
      }

      System.out.printf(
          "- index=%d, char=%s, 現在の \"%s\" 件数=%d%n", i, current, target, currentCount);
    }
  }
}
