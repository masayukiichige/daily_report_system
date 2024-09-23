package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FollowerConverter;
import actions.views.FollowerView;
import constants.JpaConst;
import models.Follower;

/**
 * フォロワーテーブルの操作に関わる処理を行うクラス
 */
public class FollowerService extends ServiceBase {

    /**
     * フォローする従業員がフォローされた従業員データを、指定されたページ数の一覧画面に表示する分取得しFollowerViewのリストで返却する
     * @param followerEmployee フォローされた従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FollowerView> getMinePerPage(EmployeeView loginEmployee, int page) {

        List<Follower> followers = em.createNamedQuery(JpaConst.Q_FOL_GET_ALL_MINE, Follower.class)
                .setParameter(JpaConst.JPQL_PARM_LOGIN_EMP, EmployeeConverter.toModel(loginEmployee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowerConverter.toViewList(followers);
    }

    /**
     * フォローした従業員がフォローされた従業員の件数を取得し、返却する
     * @param employee
     * @return フォローデータの件数
     */
    public long countAllMine(EmployeeView loginEmployee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_FOL_COUNT_ALL_MINE, Long.class)
               .setParameter(JpaConst.JPQL_PARM_LOGIN_EMP, EmployeeConverter.toModel(loginEmployee))
                .getSingleResult();


        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示するフォローされた従業員を取得し、FollowerViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<FollowerView> getAllPerPage(int page) {

        List<Follower> followers = em.createNamedQuery(JpaConst.Q_FOL_GET_ALL, Follower.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return FollowerConverter.toViewList(followers);
    }

    /**
     * フォロワーテーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long followers_count = (long) em.createNamedQuery(JpaConst.Q_FOL_COUNT, Long.class)
                .getSingleResult();
        return followers_count;
    }

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public FollowerView findOne(int id) {
        return FollowerConverter.toView(findOneInternal(id));
    }

    /**
     * フォローされた従業員のデータを1件作成し、フォロワーテーブルに登録する
     * @param fv フォロワー登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(FollowerView fv) {
            createInternal(fv);

            return null;
    }

    /**
     * フォローされた従業員のデータを更新する
     * @param rv フォロワーの更新内容
     * @return バリデーションで発生したエラーのリスト
     */


    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Follower findOneInternal(int id) {
        return em.find(Follower.class, id);
    }

    /**
     * フォロワーデータを1件登録する
     * @param fv フォロワーデータ
     */
    private void createInternal(FollowerView fv) {

        em.getTransaction().begin();
        em.persist(FollowerConverter.toModel(fv));
        em.getTransaction().commit();

    }

    /**
     * フォローされた従業員のデータを1件削除し、フォロワーテーブルから削除する
     * @param fv フォロワー登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public void destroy(FollowerView fv) {
        Follower f = findOneInternal(fv.getId());

        destroyInternal(f);

    }


    /**
     * フォロワーデータを1件削除する
     * @param fv フォロワーデータ
     */
    private void destroyInternal(Follower f) {

        em.getTransaction().begin();
        em.remove(f);   //データ削除
        em.getTransaction().commit();
        em.close();

    }
}