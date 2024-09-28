package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Follower;

/**
 * フォロワーのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class FollowerConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param fv FollowerViewのインスタンス
     * @return Followerのインスタンス
     */
    public static Follower toModel(FollowerView fv) {
        return new Follower(
                fv.getId(),
                EmployeeConverter.toModel(fv.getLoginEmployee()),
                EmployeeConverter.toModel(fv.getFollowerEmployee())
                );
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param f Followerのインスタンス
     * @return FollowertViewのインスタンス
     */
    public static FollowerView toView(Follower f) {

        if (f == null) {
            return null;
        }


        return  new FollowerView(
                f.getId(),
                EmployeeConverter.toView(f.getLoginEmployee()),
                EmployeeConverter.toView(f.getFollowerEmployee())
                );
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<FollowerView> toViewList(List<Follower> list) {
        List<FollowerView> evs = new ArrayList<>();

        for (Follower f : list) {
            evs.add(toView(f));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param f DTOモデル(コピー先)
     * @param fv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Follower f, FollowerView fv) {
        fv.setId(fv.getId());
        f.setLoginEmployee(EmployeeConverter.toModel(fv.getLoginEmployee()));
        f.setFollowerEmployee(EmployeeConverter.toModel(fv.getFollowerEmployee()));
    }

}