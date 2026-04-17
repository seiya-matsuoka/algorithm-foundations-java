package io.github.seiya_matsuoka.algorithmfoundations.algorithms;

import java.util.Arrays;

/**
 * 配列の基本操作をまとめたクラス
 *
 * <p>このクラスは今回の学習テーマの中心となる部分。
 *
 * <p>配列に対する読み取り・更新・挿入・削除を通して、 配列が「読み取りは得意だが、途中の挿入や削除では要素の移動が必要になる」 という特徴をコードで確認できるようにしている。
 */
public class ArrayBasics {
  /**
   * 指定した index の値を読み取る。
   *
   * <p>ここは配列の得意な操作を表している。インデックスが分かっていれば、目的の位置へ直接アクセスできる。
   */
  public int readAt(int[] values, int index) {
    validateIndex(values, index);
    return values[index];
  }

  /**
   * 指定した index の値を新しい値へ更新した結果を返す。
   *
   * <p>今回は学習用に、元の配列をそのまま壊さずコピーしてから更新している。「どこが変わったか」を見やすくするための作り。
   */
  public int[] updateAt(int[] values, int index, int newValue) {
    validateIndex(values, index);

    // ここは学習補助のための処理
    // 元の配列と更新後の配列を比較しやすいようにコピーを作っている。
    int[] copied = Arrays.copyOf(values, values.length);

    // ここが更新操作の本体
    copied[index] = newValue;
    return copied;
  }

  /**
   * 指定した位置に新しい値を挿入した結果を返す。
   *
   * <p>ここが配列の途中挿入を再現するコア部分。
   *
   * <p>配列は固定長なので、1件大きい配列を新しく作り、 挿入位置以降の要素を1つ右へずらしながら詰め替えている。
   */
  public int[] insertAt(int[] values, int index, int newValue) {
    if (index < 0 || index > values.length) {
      throw new IllegalArgumentException("挿入位置が不正です: " + index);
    }

    int[] inserted = new int[values.length + 1];

    // i は新しい配列側、j は元の配列側を指する。
    // 挿入位置に到達したら新しい値を入れ、それ以外は元の要素を順にコピーする。
    for (int i = 0, j = 0; i < inserted.length; i++) {
      if (i == index) {
        inserted[i] = newValue;
      } else {
        inserted[i] = values[j++];
      }
    }
    return inserted;
  }

  /**
   * 指定した位置の値を削除した結果を返す。
   *
   * <p>ここが配列の削除操作を再現するコア部分。
   *
   * <p>削除する位置以外の要素だけを新しい配列へ順に詰め直すことで、 「後ろの要素が左へ詰まる」状態を表現している。
   */
  public int[] removeAt(int[] values, int index) {
    validateIndex(values, index);
    int[] removed = new int[values.length - 1];

    // 元の配列を先頭から見ていき、削除対象 index だけを飛ばして新しい配列へ詰め直する。
    for (int i = 0, j = 0; i < values.length; i++) {
      if (i == index) {
        continue;
      }
      removed[j++] = values[i];
    }
    return removed;
  }

  /**
   * インデックスの妥当性を確認する。
   *
   * <p>これは配列操作そのものではなく、学習用コードが不正な状態で落ちないようにするための補助処理
   */
  private void validateIndex(int[] values, int index) {
    if (values == null || values.length == 0) {
      throw new IllegalArgumentException("配列は1件以上必要です。");
    }
    if (index < 0 || index >= values.length) {
      throw new IllegalArgumentException("インデックスが不正です: " + index);
    }
  }
}
