package main;

public class SQL {



    public static final String DROP_TABLE_TASKS =
            "DROP TABLE IF EXISTS Tasks";

    public static final String CREATE_TABLE_TASKS =
            "CREATE TABLE IF NOT EXISTS Tasks (" +
                    "    id BIGINT auto_increment PRIMARY KEY," +
                    "    status VARCHAR(15) NOT NULL," +
                    "    deadline DATE(10) NULL," +
                    "    task VARCHAR(100) NOT NULL" +
                    ");";
    public static final String INSERT_TASKS = "INSERT INTO Tasks " +
            "(status,deadline,task) VALUES " +
            "('pending', '2021-07-15', 'buy milk')," +
            "('pending', '2021-07-10', 'send email')," +
            "('pending', '2021-07-20', 'wash car')," +
            "('pending', '2021-07-11', 'clean house')," +
            "('pending', '2021-07-22', 'fix bathroom door');";

//See_all_pending_tasks,
    //Add_a_task_to_a_list,
    //Remove_a_task_from_a_list,
    //Mark_a_task_as_completed,
    //See_all_completed_tasks,
    //See_all_tasks
    public static final String PENDING_TASKS = "SELECT *" +
            " FROM Tasks" + " WHERE status <> 'completed' ORDER BY deadline";

    public static final String ADD_TASK = "INSERT INTO Tasks" + " (status,deadline,task) VALUES (?, ?, ?)";


    public static final String REMOVE_TASK = "DELETE FROM Tasks WHERE id = ?";


    public static final String MARK_COMPLETED = "UPDATE Tasks" +
            " SET status = 'completed', deadline = NULL WHERE id = ?";

    public static final String COMPLETED_TASKS = "SELECT *" +
            " FROM Tasks" + " WHERE status = 'completed'";

    public static final String ALL_TASKS = "SELECT * FROM Tasks;";



}




