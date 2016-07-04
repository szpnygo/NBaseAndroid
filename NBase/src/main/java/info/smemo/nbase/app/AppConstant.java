package info.smemo.nbase.app;

/**
 * Created by neo on 16/6/7.
 */
public interface AppConstant extends AppError {

    int KB = 1024;
    int MB = 1024 * KB;
    int GB = 1024 * MB;


    String TAG_HTTP = NBaseConfig.getInstance().nbaseTag + "_Http";

}
