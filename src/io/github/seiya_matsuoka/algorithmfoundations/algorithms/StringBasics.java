package io.github.seiya_matsuoka.algorithmfoundations.algorithms;

/**
 * 文字列の基本処理をまとめたクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>「文字列を先頭から末尾まで順番に見ながら、必要な情報を集計する」 という考え方をコードで再現している。
 */
public class StringBasics {
  /**
   * 文字列を1文字ずつ走査して、基本的な情報をまとめて返す。
   *
   * <p>ここが学習テーマのコア部分。 長さ、先頭文字、末尾文字、指定文字の出現回数、最初に出現した位置を 1回の走査の中でまとめて求めている。
   */
  public StringSummary summarize(String text, char target) {
    validateText(text);

    int targetCount = 0;
    int firstTargetIndex = -1;

    // ここが文字列走査の本体
    // 各文字を順番に見ながら、必要な情報を更新する。
    for (int i = 0; i < text.length(); i++) {
      char current = text.charAt(i);

      if (current == target) {
        targetCount++;

        // 最初に見つかった位置だけを保持する。
        if (firstTargetIndex == -1) {
          firstTargetIndex = i;
        }
      }
    }

    char firstChar = text.charAt(0);
    char lastChar = text.charAt(text.length() - 1);

    return new StringSummary(text.length(), firstChar, lastChar, targetCount, firstTargetIndex);
  }

  /**
   * 先頭から指定文字数ぶんの部分文字列を返す。
   *
   * <p>ここは文字列の基本操作を確認するための補助的な処理
   *
   * <p>範囲外を指定しても安全に動くよう、文字列長に合わせて切り詰める。
   */
  public String extractPrefix(String text, int length) {
    validateText(text);
    int endExclusive = Math.max(1, Math.min(length, text.length()));
    return text.substring(0, endExclusive);
  }

  /**
   * 中央付近の部分文字列を返す。
   *
   * <p>部分文字列の切り出しを確認するための補助的な処理。 長すぎないよう、最大 3 文字を取り出す。
   */
  public String extractMiddle(String text) {
    validateText(text);

    if (text.length() <= 3) {
      return text;
    }

    int start = Math.max(0, (text.length() / 2) - 1);
    int endExclusive = Math.min(text.length(), start + 3);
    return text.substring(start, endExclusive);
  }

  /**
   * 指定位置の文字を別の文字へ置き換えた新しい文字列を返す。
   *
   * <p>ここは String が不変であることを確認するための基本処理。
   *
   * <p>元の文字列そのものは変更せず、char 配列へ変換してから新しい文字列を作り直す。
   */
  public String replaceAt(String text, int index, char newChar) {
    validateText(text);
    validateIndex(text, index);

    // 元の文字列は変更できないため、まずは文字配列へ変換する。
    char[] chars = text.toCharArray();

    // ここが置き換え処理の本体
    chars[index] = newChar;

    // 置き換え後の新しい文字列を返す。
    return new String(chars);
  }

  /**
   * 文字列を反転した新しい文字列を返す。
   *
   * <p>ここでは末尾から先頭へ向かって文字を追加し、 逆順の文字列を組み立てている。
   */
  public String reverse(String text) {
    validateText(text);
    StringBuilder builder = new StringBuilder(text.length());

    // ここが反転処理の本体
    for (int i = text.length() - 1; i >= 0; i--) {
      builder.append(text.charAt(i));
    }

    return builder.toString();
  }

  /**
   * 文字列の妥当性を確認する。
   *
   * <p>これは文字列操作そのものではなく、学習用コードが不正な状態で落ちないようにするための補助処理。
   */
  private void validateText(String text) {
    if (text == null || text.isEmpty()) {
      throw new IllegalArgumentException("文字列は1文字以上必要。");
    }
  }

  /**
   * 文字位置の妥当性を確認する。
   *
   * <p>これも学習テーマそのものではなく、安全に処理を行うための補助処理。
   */
  private void validateIndex(String text, int index) {
    if (index < 0 || index >= text.length()) {
      throw new IllegalArgumentException("インデックスが不正です: " + index);
    }
  }

  /**
   * 文字列の分析結果をまとめるクラス
   *
   * <p>複数の結果を1つの戻り値として扱うための構造。
   *
   * <p>学習テーマの本体というより、結果を読みやすくまとめるための役割。
   */
  public static class StringSummary {
    private final int length;
    private final char firstChar;
    private final char lastChar;
    private final int targetCount;
    private final int firstTargetIndex;

    public StringSummary(
        int length, char firstChar, char lastChar, int targetCount, int firstTargetIndex) {
      this.length = length;
      this.firstChar = firstChar;
      this.lastChar = lastChar;
      this.targetCount = targetCount;
      this.firstTargetIndex = firstTargetIndex;
    }

    public int getLength() {
      return length;
    }

    public char getFirstChar() {
      return firstChar;
    }

    public char getLastChar() {
      return lastChar;
    }

    public int getTargetCount() {
      return targetCount;
    }

    public int getFirstTargetIndex() {
      return firstTargetIndex;
    }
  }
}
