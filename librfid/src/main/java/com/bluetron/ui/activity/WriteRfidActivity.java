package com.bluetron.ui.activity;

import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bluetron.base.activity.BaseTitleBackActivity;
import com.bluetron.core.bean.epc.EpcData;
import com.bluetron.core.bean.task.TaskListResponse;
import com.bluetron.librfid.R;
import com.bluetron.router.Navigation;
import com.bluetron.router.PathConstants;
import com.bluetron.utils.TUtils;
import com.example.liboemrfid.OemRfid;
import com.example.liboemrfid.OemType;
import com.example.liboemrfid.seuic.BaseUtil;
import com.seuic.uhf.EPC;

import org.w3c.dom.Text;

import retrofit2.http.Path;

@Route(path = PathConstants.PATH_WRITE_RFID)
public class WriteRfidActivity extends BaseTitleBackActivity {
    TextView tv_rfid_uuid, tv_rfid_data;
    EditText et_data;
    @Autowired
    byte[] ecpid;

    @Autowired
    String ecpidStr;

    /*@Autowired
    EPC epc;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_write_rfid);
    }

    @Override
    protected void initViews() {
        inflateBaseView();
        setBackVisibility(View.VISIBLE);
        setTitleTxt("写入RFID标签");
        tv_rfid_uuid = findViewById(R.id.tv_rfid_uuid);
        tv_rfid_data = findViewById(R.id.tv_rfid_data);
        et_data = findViewById(R.id.et_data);
    }

    @Override
    protected void initVariables() {
        tv_rfid_uuid.setText(ecpidStr);
        ecpid = getIntent().getByteArrayExtra("ecpid");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_write_rfid;
    }

    public void OnClickReadRfidData(View v) {
        int bank = Integer.parseInt(OemType.SECUIC_BANK);
        int address = Integer.parseInt(OemType.SECUIC_ADDRESS);
        int length = Integer.parseInt(OemType.SECUIC_LEN);

        String str_password = OemType.SECUIC_PWD;

        byte[] Epc = ecpid;

        byte[] btPassword = new byte[16];
        BaseUtil.getHexByteArray(str_password, btPassword, btPassword.length);
        byte[] buffer = new byte[OemType.SECUIC_MAX_LEN];
        if (length > OemType.SECUIC_MAX_LEN) {
            buffer = new byte[length];
        }

        String data = "";
        if (!OemRfid.client().RfidreadTagData(Epc, btPassword, bank, address, length, buffer)) {

            Toast.makeText(this, ecpid + ":读取Rfid数据失败", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getActivity(), R.string.readTagData_sucess, Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "读取Rfid数据成功", Toast.LENGTH_SHORT).show();
            data = BaseUtil.getHexString(buffer, length, " ");
            //Toast.makeText(this,data+"~~~"+TUtils.asciiToString(data)+"**",Toast.LENGTH_LONG).show();
            tv_rfid_data.setText(BaseUtil.convertHexToAsCall(data));
        }
        //epcDataList.add(new EpcData(mEPCList.get(i),data));

    }

    //写入标签
    public void OnClickWriteRfidData(View v) {

        //if (mSelectedIndex >= 0) {

                /*if (et_bank.getText().toString().isEmpty() || et_address.getText().toString().isEmpty()
                        || et_lenth.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), R.string.the_parameter_cannot_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }*/
        int bank = Integer.parseInt(OemType.SECUIC_BANK);
        int address = Integer.parseInt(OemType.SECUIC_ADDRESS);
        int length = Integer.parseInt(OemType.SECUIC_LEN);
        String str_password = OemType.SECUIC_PWD;

        byte[] Epc = ecpid;

        byte[] btPassword = new byte[16];
        BaseUtil.getHexByteArray(str_password, btPassword, btPassword.length);

        String str_data = et_data.getText().toString().replace(" ", "");
        if (str_data.isEmpty()) {
            Toast.makeText(this, "写入数据为空", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] buffer = new byte[OemType.SECUIC_MAX_LEN];
        if (length > OemType.SECUIC_MAX_LEN) {
            buffer = new byte[length];
        }
        //1223 begin
        //BaseUtil.getHexByteArray(str_data, buffer, length);
        BaseUtil.getHexByteArray(BaseUtil.string2HexStr(str_data), buffer, length);
        //1223 end
        if (!OemRfid.client().writeRfid(Epc, btPassword, bank, address, length, buffer)) {

            Toast.makeText(this, "写入数据失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "写入数据成功", Toast.LENGTH_SHORT).show();

        }
            /*} else {
                Toast.makeText(getActivity(), R.string.please_select_a_tag, Toast.LENGTH_SHORT).show();
            }*/
    }


}
