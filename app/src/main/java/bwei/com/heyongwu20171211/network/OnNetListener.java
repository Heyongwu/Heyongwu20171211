package bwei.com.heyongwu20171211.network;

import retrofit2.Call;

/**
 * Created by Yw_Ambition on 2017/12/11.
 */

public interface OnNetListener<T> {
    void Success(T t);
    void Failure(Call<T> call);
}
