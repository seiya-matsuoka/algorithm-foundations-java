package io.github.seiya_matsuoka.algorithmfoundations;

import io.github.seiya_matsuoka.algorithmfoundations.runner.ArrayRunner;
import io.github.seiya_matsuoka.algorithmfoundations.runner.ComplexityRunner;
import io.github.seiya_matsuoka.algorithmfoundations.runner.StringRunner;
import io.github.seiya_matsuoka.algorithmfoundations.runner.TopicRunner;
import io.github.seiya_matsuoka.algorithmfoundations.runner.TraversalRunner;

/**
 * リポジトリ全体の共通エントリーポイント
 *
 * <p>このクラスの役割は、コマンドライン引数を読み取り、 どの学習トピックを実行するかを判定して対応する runner に処理を渡すこと。
 *
 * <p>アルゴリズム本体の処理は持たず、実行の振り分けだけを担当する。
 */
public class App {
  public static void main(String[] args) {
    // 実行時オプションを最初にまとめて読み取る。
    RunnerOptions options = parseOptions(args);

    // topic が未指定の場合は、何を実行するか判断できないため使い方を表示する。
    if (options.getTopic() == null || options.getTopic().isBlank()) {
      printUsage();
      return;
    }

    // topic 名から、対応する runner を決定する。
    TopicRunner runner = resolveRunner(options.getTopic());
    if (runner == null) {
      System.out.println("未対応の topic が指定されました: " + options.getTopic());
      System.out.println("実行できる topic は traversal, array, string, complexity です。\n");
      printUsage();
      return;
    }

    // 実際の学習処理は各 runner に任せる。
    runner.run(options);
  }

  /**
   * コマンドライン引数を簡易的に解析する。
   *
   * <p>外部ライブラリを使わず、学習用として最低限必要なオプションだけを 自前で読み取る構成としている。
   */
  private static RunnerOptions parseOptions(String[] args) {
    String topic = null;
    String input = null;
    boolean trace = false;
    String target = null;
    Integer size = null;

    for (int i = 0; i < args.length; i++) {
      String arg = args[i];

      switch (arg) {
        // 実行したい学習トピック名を読み取る。
        case "--topic" -> {
          if (i + 1 < args.length) {
            topic = args[++i];
          }
        }
        // 入力値を直接指定したい場合に使用する。
        case "--input" -> {
          if (i + 1 < args.length) {
            input = args[++i];
          }
        }
        // 処理途中の流れを表示したい場合に使用する。
        case "--trace" -> trace = true;
        // 探索対象や比較対象の値を指定したい場合に使用する。
        case "--target" -> {
          if (i + 1 < args.length) {
            target = args[++i];
          }
        }
        // 大きめの入力データを自動生成したい場合のサイズ指定とする。
        case "--size" -> {
          if (i + 1 < args.length) {
            try {
              size = Integer.parseInt(args[++i]);
            } catch (NumberFormatException ex) {
              System.out.println("--size には整数を指定してください。指定値: " + args[i]);
              return new RunnerOptions(topic, input, trace, target, null);
            }
          }
        }
        default -> {
          // 現時点で未対応のオプションは無視する。
          // 学習用の最小構成を保つため、ここでは厳密なエラーにはしない。
        }
      }
    }

    return new RunnerOptions(topic, input, trace, target, size);
  }

  /**
   * topic 名に応じて実行する runner を選ぶ。
   *
   * <p>ここは「どの学習トピックを走らせるか」を決めるルーティング部分。
   */
  private static TopicRunner resolveRunner(String topic) {
    return switch (topic) {
      case "traversal" -> new TraversalRunner();
      case "array" -> new ArrayRunner();
      case "string" -> new StringRunner();
      case "complexity" -> new ComplexityRunner();
      default -> null;
    };
  }

  /** 使い方を表示する。 */
  private static void printUsage() {
    System.out.println("使い方:");
    System.out.println(
        "  java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic <topic>"
            + " [options]");
    System.out.println();
    System.out.println("現在指定できる topic:");
    System.out.println("  traversal   : 走査・集計の基本を学ぶ");
    System.out.println("  array       : 配列の基本を学ぶ");
    System.out.println("  string      : 文字列の基本を学ぶ");
    System.out.println("  complexity  : 計算量の基本を学ぶ");
    System.out.println();
    System.out.println("共通オプション:");
    System.out.println("  --input <値>    入力値を直接指定する");
    System.out.println("  --trace         処理途中の流れを表示する");
    System.out.println("  --target <値>   探索や集計で使う対象値を指定する");
    System.out.println("  --size <値>     指定したサイズで入力データを生成する");
    System.out.println();
    System.out.println("例:");
    System.out.println(
        "  java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic traversal"
            + " --trace");
    System.out.println(
        "  java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic array --input"
            + " 5,3,8,1,4");
    System.out.println(
        "  java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic string --input"
            + " algorithm --target a");
    System.out.println(
        "  java -cp out io.github.seiya_matsuoka.algorithmfoundations.App --topic complexity --size"
            + " 1000");
  }
}
