package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.FollowerView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EmployeeService;
import services.FollowerService;
import services.ReportService;

/**
 * フォローされた日報に関する処理を行うActionクラス
 *
 */
public class FollowerAction extends ActionBase {

    private FollowerService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new FollowerService();

        //メソッドを実行
        invoke();
        service.close();
     }


    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();
        EmployeeView loginEmployee = (EmployeeView)getSessionScope(AttributeConst.LOGIN_EMP);

        List<FollowerView> followers = service.getMinePerPage(loginEmployee, page);



        //全フォロワーの件数を取得
        long followerCount = service.countAllMine(loginEmployee);

        putRequestScope(AttributeConst.FOLLOWERS, followers); //取得したフォローする人のデータ
        putRequestScope(AttributeConst.FOL_COUNT, followerCount); //ログイン中の従業員がフォローした人の数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_FOL_INDEX);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {


            //セッションからログイン中の従業員情報を取得
            EmployeeView ev1 = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);


          //セッションからフォローされた従業員情報を取得
              //idを条件に従業員データを取得する
              //EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.EMP_ID);

            EmployeeService service2 = new EmployeeService();

           //idを条件に従業員データを取得する
               EmployeeView ev2 = service2.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));
               service2.close();

                if (ev2 == null || ev2.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {
                    //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                                   forward(ForwardConst.FW_ERR_UNKNOWN);
                    return;
                }
                putRequestScope(AttributeConst.FOLLOWER_EMP, ev2); //取得した従業員情報



            //パラメータの値を元に従業員情報のインスタンスを作成する
            FollowerView fv = new FollowerView(
                    null,
                    ev1,
                    ev2);

          //フォロー情報登録
            List<String> errors = service.create(fv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.FOLLOWER, fv);//入力されたフォロワー情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_EMP_INDEX);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

             // フォローフラグをTRUEにする
                putRequestScope(AttributeConst.FOL_FLG, AttributeConst.FOL_FLAG_TRUE);

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EMP, ForwardConst.FW_EMP_SHOW);

            }

    }
    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {


            ReportService service1 = new ReportService();


            //セッションからフォロー中の従業員情報を取得
            EmployeeView followerEmployee = (EmployeeView) getSessionScope(AttributeConst.FOLLOWER_EMP);


          //フォロー中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する
            int page = getPage();
            List<ReportView> reports = service1.getMinePerPage(followerEmployee, page);



            //フォロー中の従業員が作成した日報データの件数を取得
            long followerReportsCount = service1.countAllMine(followerEmployee);

            service1.close();

            putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
            putRequestScope(AttributeConst.REP_COUNT, followerReportsCount); //ログイン中の従業員が作成した日報の数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数


            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_FOL_SHOW);
    }


    /**
     * フォローされた従業員かどうかチェックし、フォローされた従業員でなければエラー画面を表示
     * true: フォローされた従業員 false: フォローされた従業員ではない
     * @throws ServletException
     * @throws IOException
     */
    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {


                //idを条件に従業員データを削除する
                //service.destroy(toNumber(getRequestParam(AttributeConst.FOL_ID)));
                FollowerView fv = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));
                service.destroy(fv);


                //セッションに削除完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

                // フォローフラグをFALSEにする
                putRequestScope(AttributeConst.FOL_FLG, AttributeConst.FOL_FLAG_FALSE);
                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EMP, ForwardConst.FW_EMP_SHOW);


        }
}