package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * フォローした従業員情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class FollowerView {

    /**
     * id
     */
    private Integer id;

    /**
     * ログインしている従業員（フォローした従業員）
     */
    private EmployeeView loginEmployee;

    /**
     * フォローされた従業員（フォローされた従業員）
     */
    private EmployeeView followerEmployee;


}