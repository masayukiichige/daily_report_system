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
 * フォローされた従業員に関する処理を行うActionクラス
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

           //idを条件に従業員データを取得する
            EmployeeService service2 = new EmployeeService();
            EmployeeView ev2 = service2.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));
            service2.close();

                if (ev2 == null || ev2.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {
                    //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                                   forward(ForwardConst.FW_ERR_UNKNOWN);
                    return;
                }
               // putRequestScope(AttributeConst.FOLLOWER_EMP, ev2); //取得した従業員情報



            //パラメータの値を元に従業員情報のインスタンスを作成する
            FollowerView fv = new FollowerView(
                    null,
                    ev1,
                    ev2);

          //フォロー情報登録
            service.create(fv);

                //セッションに登録完了のフラッシュメッセージを設定
                //putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);

    }
    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {


        ReportService service1 = new ReportService();

        EmployeeService service2 = new EmployeeService();

        int page = getPage();

            //idを条件に従業員データを取得する
        if(page ==1 ){
            EmployeeView employee = service2.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));
            putSessionScope(AttributeConst.FOLLOWER_EMP, employee); //取得した従業員情報
        }
          //セッションからログイン中の従業員情報を取得
            EmployeeView employee = (EmployeeView) getSessionScope(AttributeConst.FOLLOWER_EMP);


            service2.close();

          //フォロー中の従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得する


            List<ReportView> reports = service1.getMinePerPage(employee, page);

            //フォロー中の従業員が作成した日報データの件数を取得
            long ReportsCount = service1.countAllMine(employee);

            service1.close();

            putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
            putRequestScope(AttributeConst.REP_COUNT, ReportsCount); //指定従業員が作成した日報の数
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
     * フォローされた従業員をフォローしなくする場合にその従業員をテーブルから削除する。
     * @throws ServletException
     * @throws IOException
     */
    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

      //idを条件に従業員データを取得する
        EmployeeService service2 = new EmployeeService();
        EmployeeView ev = service2.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));
        service2.close();

        if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {
            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                           forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        FollowerView fv = service.getFollowerMine(loginEmployee, ev);

        service.destroy(fv);

        //セッションに削除完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);


        }
}