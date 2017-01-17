package Email.Models;

import javax.activation.DataSource;

/**
 * Created by v_frankyfu on 2017/1/14.
 * 邮件附件model
 */
public class Attachment {
    private final String name;
    private final DataSource dataSource;

    /**
     *
     * @param name
     * @param dataSource
     */
    public Attachment(final String name, final DataSource dataSource) {
        this.name = name;
        this.dataSource = dataSource;
    }

    public DataSource GetDataSource() {
        return dataSource;
    }

    public String GetName() {
        return name;
    }
}
