package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "daily_report_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_EMP = "employees"; //テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //社員番号
    String EMP_COL_NAME = "name"; //氏名
    String EMP_COL_PASS = "password"; //パスワード
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String EMP_COL_CREATED_AT = "created_at"; //登録日時
    String EMP_COL_UPDATED_AT = "updated_at"; //更新日時
    String EMP_COL_DELETE_FLAG = "delete_flag"; //削除フラグ


    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int EMP_DEL_TRUE = 1; //削除フラグON(削除済み)
    int EMP_DEL_FALSE = 0; //削除フラグOFF(現役)

    int FOL_FLG_TRUE = 1;//フォローフラグON(フォローする)
    int FOL_FLG_FALSE = 0;//フォローフラグOFF(フォローしない)


    //日報テーブル
    String TABLE_REP = "reports"; //テーブル名
    //日報テーブルカラム
    String REP_COL_ID = "id"; //id
    String REP_COL_EMP = "employee_id"; //日報を作成した従業員のid
    String REP_COL_REP_DATE = "report_date"; //いつの日報かを示す日付
    String REP_COL_TITLE = "title"; //日報のタイトル
    String REP_COL_CONTENT = "content"; //日報の内容
    String REP_COL_CREATED_AT = "created_at"; //登録日時
    String REP_COL_UPDATED_AT = "updated_at"; //更新日時

    // フォロー従業員一覧機能追加オプション
    //フォロワーテーブル：追加
    String TABLE_FOL = "followers"; //テーブル名
    //フォロワーテーブルカラム：追加
    String FOL_COL_ID = "id"; //id
    String FOL_COL_LOGIN_EMP = "loginEmployee"; //ログインした従業員
    String FOL_COL_FOLLOWER_EMP = "followerEmployee"; //フォローされた従業員


    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_REP = "report"; //日報
    String ENTITY_FOL = "follower"; //フォロワー:追加

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_EMPLOYEE = "employee"; //従業員
    String JPQL_PARM_FOLLOWER = "followerEmployee"; //フォロワー
    String JPQL_PARM_LOGIN_EMP = "loginEmployee"; //ログイン従業員


    //NamedQueryの nameとquery
    //全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC"; //query
    //全ての従業員の件数を取得する
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";
    //社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定した社員番号を保持する従業員の件数を取得する
    String Q_EMP_COUNT_REGISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;

    //全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r ORDER BY r.id DESC";
    //全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r";
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;

    // フォロー社員日報一覧機能追加オプション
    //全てのフォロー従業員をidの降順に取得する
    String Q_FOL_GET_ALL = ENTITY_FOL + ".getAll";
    String Q_FOL_GET_ALL_DEF = "SELECT f FROM Follower AS f ORDER BY f.id DESC";
    //全てのフォロー従業員の件数を取得する
    String Q_FOL_COUNT = ENTITY_FOL + ".count";
    String Q_FOL_COUNT_DEF = "SELECT COUNT(f) FROM Follower AS f";
    //ログインした従業員がフォローした従業員の全件idの降順で取得する
    String Q_FOL_GET_ALL_MINE = ENTITY_FOL + ".getAllMine";
    String Q_FOL_GET_ALL_MINE_DEF = "SELECT f FROM Follower AS f WHERE f.loginEmployee = :" + JPQL_PARM_LOGIN_EMP + " ORDER BY f.id DESC";
    //ログインした社員がフォローした社員の件数を取得する
    String Q_FOL_COUNT_ALL_MINE = ENTITY_FOL + ".countAllMine";
    String Q_FOL_COUNT_ALL_MINE_DEF = "SELECT COUNT(f) FROM Follower AS f WHERE f.loginEmployee = :" + JPQL_PARM_LOGIN_EMP;

    //ログインした従業員がフォローした従業員中の指定従業員の件数を取得する
    String Q_FOL_COUNT_FOL_MINE = ENTITY_FOL + ".countFollowerMine";
    String Q_FOL_COUNT_FOL_MINE_DEF = "SELECT COUNT(f) FROM Follower AS f WHERE f.loginEmployee = :" + JPQL_PARM_LOGIN_EMP + " AND f.followerEmployee = :" + JPQL_PARM_FOLLOWER;

    //ログイン従業員、指定したフォロー従業員を条件に未削除の従業員を1件取得する
    String Q_FOL_GET_FOL_MINE = ENTITY_FOL + ".getFollowerMine";
    String Q_FOL_GET_FOL_MINE_DEF = "SELECT f FROM Follower AS f WHERE f.loginEmployee = :" + JPQL_PARM_LOGIN_EMP + " AND f.followerEmployee = :" + JPQL_PARM_FOLLOWER;

}
