package jinke.com.tv_health_jkcommunity.ui.activity;

import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jinke.com.tv_health_jkcommunity.R;
import jinke.com.tv_health_jkcommunity.base.BaseActivity;
import jinke.com.tv_health_jkcommunity.bean.ConnectDataBean;
import jinke.com.tv_health_jkcommunity.bean.LineBean;
import jinke.com.tv_health_jkcommunity.jpserver.CommunityMsgReceiver;
import jinke.com.tv_health_jkcommunity.presenter.MainPresenter;
import jinke.com.tv_health_jkcommunity.socket.JpHelper;
import jinke.com.tv_health_jkcommunity.socket.SocketHelper;
import jinke.com.tv_health_jkcommunity.utils.DateUtil;
import jinke.com.tv_health_jkcommunity.utils.LineChartManager;
import jinke.com.tv_health_jkcommunity.utils.LogUtils;
import jinke.com.tv_health_jkcommunity.utils.ToastUtils;
import jinke.com.tv_health_jkcommunity.view.MainView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements SocketHelper.SocketListener {
    @BindView(R.id.img_connect_index)
    ImageView connectIndex;
    @BindView(R.id.img_heart_index)
    ImageView imgIndex;
    @BindView(R.id.img_heart)
    ImageView imgHeart;

    @BindView(R.id.tx_height_weight)
    TextView txHeightWeight;
    @BindView(R.id.tx_bmi_value)
    TextView txBmiValue;
    @BindView(R.id.tx_bmi_date)
    TextView txBmiDate;
    @BindView(R.id.tx_blood_church_value)
    TextView txBloodChurchValue;
    @BindView(R.id.tx_blood_church_date)
    TextView txBloodChurchDate;
    @BindView(R.id.tx_blood_oxygen_value)
    TextView txBloodOxygenValue;
    @BindView(R.id.tx_heart_rate)
    TextView txHeartRate;
    @BindView(R.id.tx_blood_oxygen_date)
    TextView txBloodOxygenDate;
    @BindView(R.id.tx_blood_pressure_value)
    TextView txBloodPressureValue;
    @BindView(R.id.tx_pressure_heart_rate)
    TextView txPressureHeartRate;
    @BindView(R.id.tx_blood_pressure_date)
    TextView txBloodPressureDate;
    @BindView(R.id.line_chart_bmi)
    LineChart lineChartBmi;
    @BindView(R.id.line_chart_blood_church)
    LineChart lineChartBloodChurch;
    @BindView(R.id.line_chart_blood_oxygen)
    LineChart lineChartBloodOxygen;
    @BindView(R.id.line_chart_blood_pressure)
    LineChart lineChartBloodPressure;
    @BindView(R.id.line_chart_healthy_cottage)
    LineChart lineChartHealthyCottage;
    @BindView(R.id.line_chart_online_consultation)
    LineChart lineChartOnlineConsultation;
    @BindView(R.id.line_chart_community_hospital)
    LineChart lineChartCommunityHospital;
    @BindView(R.id.line_chart_top_three_hospitals)
    LineChart lineChartTopThreeHospitals;
    @BindView(R.id.image_blood_pressure_index)
    ImageView imagePressureIndex;
    @BindView(R.id.image_bmi_index)
    ImageView imageBmiIndex;
    @BindView(R.id.image_blood_church_index)
    ImageView imageChurchIndex;
    @BindView(R.id.image_blood_oxygen_index)
    ImageView imageOxygenIndex;
    @BindView(R.id.tx_id)
    TextView txId;
    @BindView(R.id.tx_name)
    TextView txName;
    @BindView(R.id.tx_sex)
    TextView txSex;
    @BindView(R.id.tx_age)
    TextView txAge;
    @BindView(R.id.tx_address)
    TextView txAddress;
    @BindView(R.id.tx_date)
    TextView txDate;
    @BindView(R.id.tx_bmi_time)
    TextView txBmiTime;
    @BindView(R.id.tx_blood_church_time)
    TextView txBloodChurchTime;
    @BindView(R.id.tx_blood_oxygen_time)
    TextView txBloodOxygenTime;
    @BindView(R.id.tx_blood_pressure_time)
    TextView txBloodPressureTime;
    @BindView(R.id.image_man)
    ImageView imageMan;

    private SocketHelper socketHelper;
    private boolean indexFlag = true;
    private boolean connectFlag = true;
    private boolean heartFlag = true;

    @Override
    protected int childSetContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void childInitView() throws IOException, URISyntaxException {
        initSocket();
    }

    @Override
    public void onMessage(ConnectDataBean bean) {
        initBaseInfo(bean.getData().getUserInfo());
//        //右侧数据表
        initHealthyCottageChart(bean.getData().getHealthData());
        initOnlineConsultationChart(bean.getData().getOnlineData());
        initCommunityHospitalChart(bean.getData().getCommunityData());
        initTopThreeHospitalsChart(bean.getData().getStepTreeData());

        initBmi(bean.getData().getBmiData());
        initChurch(bean.getData().getBloodglucoseData());
        initOxygen(bean.getData().getBloodoxygensaturationData());
        initPressure(bean.getData().getBloodPressureData());
        if (connectFlag) {
            connectIndex.setImageResource(R.drawable.circle_blue);
            connectFlag = false;
        } else {
            connectIndex.setImageResource(R.drawable.circle_yellow);
            connectFlag = true;
        }
    }

    @Override
    public void onError() {
        ToastUtils.showShort(this, "连接服务器失败！");
        try {
            initSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        imgIndex.setImageResource(R.drawable.circle_red);
        connectIndex.setImageResource(R.drawable.circle_red);
    }


    @Override
    public void onHeart() {
        if (indexFlag) {
            imgIndex.setImageResource(R.drawable.circle_blue);
            indexFlag = false;
        } else {
            imgIndex.setImageResource(R.drawable.circle_green);
            indexFlag = true;
        }
    }

    @Override
    public void connect() {
    }

    @Override
    public void sentHeart() {
        if (heartFlag) {
            imgHeart.setImageResource(R.drawable.circle_blue);
            heartFlag = false;
        } else {
            imgHeart.setImageResource(R.drawable.circle_green);
            heartFlag = true;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    private void initSocket() throws IOException, URISyntaxException {
        socketHelper = new SocketHelper(this);
    }

    //健康小屋
    private List<ConnectDataBean.DataBean.HealthDataBeanX.HealthDataBean> healthDataList;
    private List<String> healthDataXList;

    private void initHealthyCottageChart(ConnectDataBean.DataBean.HealthDataBeanX communityData) {
        if (communityData != null && communityData.getHealthData() != null) {
            ConnectDataBean.DataBean.HealthDataBeanX.HealthDataBean bean = communityData.getHealthData().get(0);
            if (bean != null) {
                if (communityData.getHealthData() != null)
                    healthDataList = communityData.getHealthData();
                healthDataXList = new ArrayList<>();
                if (healthDataList != null)
                    for (int x = 0; x < communityData.getHealthData().size(); x++) {
                        healthDataXList.add(communityData.getHealthData().get(communityData.getHealthData().size() - 1 - x).getMon());
                    }
                if (healthDataList != null && healthDataList != null) {
                    ArrayList<Entry> point = new ArrayList<>();
                    float max = 0;
                    for (int x = 0; x < healthDataList.size(); x++) {
                        if (healthDataList.get(healthDataList.size() - 1 - x) != null && healthDataList.get(healthDataList.size() - 1 - x).getServiceCount() != null) {
                            point.add(new Entry((float) x, Float.parseFloat(healthDataList.get(healthDataList.size() - 1 - x).getServiceCount())));
                            max = Float.parseFloat(String.valueOf(healthDataList.get(healthDataList.size() - 1 - x).getServiceCount())) > max ? Float.parseFloat(String.valueOf(healthDataList.get(healthDataList.size() - 1 - x).getServiceCount())) : max;
                        }
                    }
                    if (point.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.setLineCubicMode(LineDataSet.Mode.LINEAR);
                        lineChartManager.setCircleVisiable(false);
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initSingleLineChart(this, lineChartHealthyCottage, point, (ArrayList<String>) healthDataXList);
                    }
                }
            }
        }
    }

    //在线问诊
    private List<ConnectDataBean.DataBean.OnlineDataBean.OnlineDiagnoseDataBean> onLineList;
    private List<String> onLineXList;

    private void initOnlineConsultationChart(ConnectDataBean.DataBean.OnlineDataBean onlineData) {
        if (onlineData != null && onlineData.getOnlineDiagnoseData() != null) {
            ConnectDataBean.DataBean.OnlineDataBean.OnlineDiagnoseDataBean bean = onlineData.getOnlineDiagnoseData().get(0);
            if (bean != null) {
                if (onlineData.getOnlineDiagnoseData() != null)
                    onLineList = onlineData.getOnlineDiagnoseData();
                onLineXList = new ArrayList<>();
                if (onLineList != null)
                    for (int x = 0; x < onlineData.getOnlineDiagnoseData().size(); x++) {
                        onLineXList.add(onlineData.getOnlineDiagnoseData().get(onlineData.getOnlineDiagnoseData().size() - 1 - x).getMon());
                    }
                if (onLineList != null && onLineList != null) {
                    ArrayList<Entry> point = new ArrayList<>();
                    float max = 0;
                    for (int x = 0; x < onLineList.size(); x++) {
                        if (onLineList.get(onLineList.size() - 1 - x) != null && onLineList.get(onLineList.size() - 1 - x).getServiceCount() != null) {
                            point.add(new Entry((float) x, Float.parseFloat(onLineList.get(onLineList.size() - 1 - x).getServiceCount())));
                            max = Float.parseFloat(String.valueOf(onLineList.get(onLineList.size() - 1 - x).getServiceCount())) > max ? Float.parseFloat(String.valueOf(onLineList.get(onLineList.size() - 1 - x).getServiceCount())) : max;
                        }
                    }
                    if (point.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.setLineCubicMode(LineDataSet.Mode.LINEAR);
                        lineChartManager.setCircleVisiable(false);
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initSingleLineChart(this, lineChartOnlineConsultation, point, (ArrayList<String>) onLineXList);
                    }
                }
            }
        }
    }

    //社区医院
    private List<ConnectDataBean.DataBean.CommunityDataBean.HospitalDataBean> communityList;
    private List<String> communityXList;

    private void initCommunityHospitalChart(ConnectDataBean.DataBean.CommunityDataBean communityData) {
        if (communityData != null && communityData.getHospitalData() != null) {
            ConnectDataBean.DataBean.CommunityDataBean.HospitalDataBean bean = communityData.getHospitalData().get(0);
            if (bean != null) {
                if (communityData.getHospitalData() != null)
                    communityList = communityData.getHospitalData();
                communityXList = new ArrayList<>();
                if (communityList != null)
                    for (int x = 0; x < communityData.getHospitalData().size(); x++) {
                        communityXList.add(communityData.getHospitalData().get(communityData.getHospitalData().size() - 1 - x).getMon());
                    }
                if (communityList != null && communityList != null) {
                    ArrayList<Entry> point = new ArrayList<>();
                    float max = 0;
                    for (int x = 0; x < communityList.size(); x++) {
                        if (communityList.get(communityList.size() - 1 - x) != null && communityList.get(communityList.size() - 1 - x).getServiceCount() != null) {
                            point.add(new Entry((float) x, Float.parseFloat(communityList.get(communityList.size() - 1 - x).getServiceCount())));
                            max = Float.parseFloat(String.valueOf(communityList.get(communityList.size() - 1 - x).getServiceCount())) > max ? Float.parseFloat(String.valueOf(communityList.get(communityList.size() - 1 - x).getServiceCount())) : max;
                        }
                    }
                    if (point.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.setLineCubicMode(LineDataSet.Mode.LINEAR);
                        lineChartManager.setCircleVisiable(false);
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initSingleLineChart(this, lineChartCommunityHospital, point, (ArrayList<String>) communityXList);
                    }
                }
            }
        }
    }

    //三甲医院
    private List<ConnectDataBean.DataBean.StepTreeDataBean.HigherHospitalDataBean> threeList;
    private List<String> threeXList;

    private void initTopThreeHospitalsChart(ConnectDataBean.DataBean.StepTreeDataBean stepTreeData) {
        if (stepTreeData != null && stepTreeData.getHigherHospitalData() != null) {
            ConnectDataBean.DataBean.StepTreeDataBean.HigherHospitalDataBean bean = stepTreeData.getHigherHospitalData().get(0);
            if (bean != null) {
                if (stepTreeData.getHigherHospitalData() != null)
                    threeList = stepTreeData.getHigherHospitalData();
                threeXList = new ArrayList<>();
                if (threeList != null)
                    for (int x = 0; x < stepTreeData.getHigherHospitalData().size(); x++) {
                        threeXList.add(stepTreeData.getHigherHospitalData().get(stepTreeData.getHigherHospitalData().size() - 1 - x).getMon());
                    }

                if (threeList != null && threeList != null) {
                    ArrayList<Entry> point = new ArrayList<>();
                    float max = 0;
                    for (int x = 0; x < threeList.size(); x++) {
                        if (threeList.get(threeList.size() - 1 - x) != null && threeList.get(threeList.size() - 1 - x).getServiceCount() != null) {
                            point.add(new Entry((float) x, Float.parseFloat(threeList.get(threeList.size() - 1 - x).getServiceCount())));
                            max = Float.parseFloat(String.valueOf(threeList.get(threeList.size() - 1 - x).getServiceCount())) > max ? Float.parseFloat(String.valueOf(threeList.get(threeList.size() - 1 - x).getServiceCount())) : max;
                        }
                    }
                    if (point.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.setLineCubicMode(LineDataSet.Mode.LINEAR);
                        lineChartManager.setCircleVisiable(false);
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initSingleLineChart(this, lineChartTopThreeHospitals, point, (ArrayList<String>) threeXList);
                    }
                }
            }
        }
    }

    //设置血氧
    private List<ConnectDataBean.DataBean.BloodoxygensaturationDataBean.BloodoxygensaturationListBean> oxygenList;
    private ArrayList<LineBean> oxygenLineList;
    private List<String> oxygenXList;

    private void initOxygen(ConnectDataBean.DataBean.BloodoxygensaturationDataBean oxygenBean) {
        if (oxygenBean != null && oxygenBean.getBloodoxygensaturationList() != null && oxygenBean.getShowX() != null) {
            ConnectDataBean.DataBean.BloodoxygensaturationDataBean.BloodoxygensaturationListBean bean = oxygenBean.getBloodoxygensaturationList().get(0);
            if (bean != null) {
                if (bean.getSaturationValue() != null)
                    txBloodOxygenValue.setText(bean.getSaturationValue());
                if (bean.getPulseRateValue() != null)
                    txHeartRate.setText(bean.getPulseRateValue());
                if (bean.getTestTime() != null) {
                    String dates = bean.getTestTime();
                    txBloodOxygenDate.setText(dates.substring(dates.indexOf("-") + 1, dates.indexOf(" ")).replace("-", "/"));
                    txBloodOxygenTime.setText(dates.substring(dates.indexOf(" "), dates.lastIndexOf(":")));
                }
                if (oxygenBean.getBloodoxygensaturationList() != null)
                    oxygenList = oxygenBean.getBloodoxygensaturationList();
                oxygenXList = new ArrayList<>();
                if (oxygenList != null)
                    for (int x = 0; x < oxygenList.size(); x++) {
                        if (oxygenList.get(oxygenList.size() - 1 - x) != null && oxygenList.get(oxygenList.size() - 1 - x).getTestTime() != null) {
                            String date = oxygenList.get(oxygenList.size() - 1 - x).getTestTime().replace("-", "/");
                            oxygenXList.add(date.substring(date.indexOf("/") + 1, date.indexOf(" ")));
                        }
                    }
                if (bean.getOxygensaturationStatus() != null)
                    Picasso.with(this).load(bean.getOxygensaturationStatus().equals("正常") ? R.mipmap.icon_oxygen_1
                            : R.mipmap.icon_oxygen_2).into(imageOxygenIndex);
                if (oxygenList != null && oxygenList != null) {
                    oxygenLineList = new ArrayList<>();
                    ArrayList<Entry> point = new ArrayList<>();
                    float max = 0;
                    for (int x = 0; x < oxygenList.size(); x++) {
                        point.add(new Entry((float) x, Float.parseFloat(oxygenList.get(x).getSaturationValue())));
                        max = Float.parseFloat(String.valueOf(oxygenList.get(x).getSaturationValue())) > max ? Float.parseFloat(String.valueOf(oxygenList.get(x).getSaturationValue())) : max;
                    }
                    oxygenLineList.add(new LineBean(point, R.color.line_chart_line_color1));
                    ArrayList<Entry> pointHeart = new ArrayList<>();
                    for (int x = 0; x < oxygenList.size(); x++) {
                        if (oxygenList.get(x) != null && oxygenList.get(x).getPulseRateValue() != null) {
                            pointHeart.add(new Entry((float) x, Float.parseFloat(oxygenList.get(x).getPulseRateValue())));
                            max = Float.parseFloat(String.valueOf(oxygenList.get(x).getPulseRateValue())) > max ? Float.parseFloat(String.valueOf(oxygenList.get(x).getPulseRateValue())) : max;
                        }
                    }
                    oxygenLineList.add(new LineBean(pointHeart, R.color.line_chart_line_color2));
                    if (oxygenLineList != null && oxygenLineList.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initLineSChart(this, lineChartBloodOxygen, oxygenLineList, (ArrayList<String>) oxygenXList);
                    }
                }
            }
        }
    }

    //设置血压
    private List<ConnectDataBean.DataBean.BloodPressureDataBean.BloodPressureListBean> pressureList;
    private ArrayList<LineBean> pressureLineList;
    private List<String> pressureXList;

    private void initPressure(ConnectDataBean.DataBean.BloodPressureDataBean bloodPressureData) {
        if (bloodPressureData != null && bloodPressureData.getBloodPressureList() != null && bloodPressureData.getShowX() != null) {
            ConnectDataBean.DataBean.BloodPressureDataBean.BloodPressureListBean bean = bloodPressureData.getBloodPressureList().get(0);
            if (bean != null) {
                if (bean.getHighValue() != null && bean.getLowValue() != null)
                    txBloodPressureValue.setText(bean.getHighValue() + "/" + bean.getLowValue());
                if (bean.getTestTime() != null) {
                    String dates = bean.getTestTime();
                    txBloodPressureDate.setText(dates.substring(dates.indexOf("-") + 1, dates.indexOf(" ")).replace("-", "/"));
                    txBloodPressureTime.setText(dates.substring(dates.indexOf(" "), dates.lastIndexOf(":")));
                }
                txPressureHeartRate.setText(bean.getPulseRateValue() + "");
                if (bloodPressureData.getBloodPressureList() != null)
                    pressureList = bloodPressureData.getBloodPressureList();
                pressureXList = new ArrayList<>();
                if (pressureList != null)
                    for (int x = 0; x < pressureList.size(); x++) {
                        String date = pressureList.get(pressureList.size() - 1 - x).getTestTime().replace("-", "/");
                        pressureXList.add(date.substring(date.indexOf("/") + 1, date.indexOf(" ")));
                    }
                if (bean.getPressureStatus() != null)
                    Picasso.with(this).load(bean.getPressureStatus().equals("正常") ? R.mipmap.icon_pressure_1
                            : bean.getPressureStatus().equals("临界高血压") ? R.mipmap.icon_pressure_2
                            : bean.getPressureStatus().equals("高血压1期") ? R.mipmap.icon_pressure_3
                            : bean.getPressureStatus().equals("为高血压2期") ? R.mipmap.icon_pressure_4
                            : R.mipmap.icon_pressure_5).into(imagePressureIndex);
                if (pressureList != null && pressureList != null) {
                    float max = 0;
                    pressureLineList = new ArrayList<>();
                    ArrayList<Entry> pointPulseRate = new ArrayList<>();
                    for (int x = 0; x < pressureList.size(); x++) {
                        if (pressureList.get(x) != null) {
                            pointPulseRate.add(new Entry((float) x, Float.parseFloat(String.valueOf(pressureList.get(x).getPulseRateValue()))));
                            max = Float.parseFloat(String.valueOf(pressureList.get(x).getPulseRateValue())) > max ? Float.parseFloat(String.valueOf(pressureList.get(x).getPulseRateValue())) : max;
                        }
                    }
                    if (pointPulseRate != null && pointPulseRate.size() > 0)
                        pressureLineList.add(new LineBean(pointPulseRate, R.color.line_chart_line_color1));

                    ArrayList<Entry> pointHigh = new ArrayList<>();
                    for (int x = 0; x < pressureList.size(); x++) {
                        if (pressureList.get(x) != null && pressureList.get(x).getHighValue() != null) {
                            pointHigh.add(new Entry((float) x, Float.parseFloat(String.valueOf(pressureList.get(x).getHighValue()))));
                            max = Float.parseFloat(String.valueOf(pressureList.get(x).getHighValue())) > max ? Float.parseFloat(String.valueOf(pressureList.get(x).getHighValue())) : max;
                        }
                    }
                    if (pointHigh != null && pointHigh.size() > 0)
                        pressureLineList.add(new LineBean(pointHigh, R.color.line_chart_line_color2));

                    ArrayList<Entry> poinLow = new ArrayList<>();
                    for (int x = 0; x < pressureList.size(); x++) {
                        if (pressureList.get(x).getLowValue() != null) {
                            if (pressureList.get(x) != null && pressureList.get(x).getLowValue() != null) {
                                poinLow.add(new Entry((float) x, Float.parseFloat(String.valueOf(pressureList.get(x).getLowValue()))));
                                max = Float.parseFloat(String.valueOf(pressureList.get(x).getLowValue())) > max ? Float.parseFloat(String.valueOf(pressureList.get(x).getLowValue())) : max;
                            }
                        }
                    }
                    if (poinLow != null && poinLow.size() > 0)
                        pressureLineList.add(new LineBean(poinLow, R.color.line_chart_line_color3));
                    if (pressureLineList != null && pressureLineList.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initLineSChart(this, lineChartBloodPressure, pressureLineList, (ArrayList<String>) pressureXList);
                    }
                }
            }
        }

    }

    //设置血糖
    private List<ConnectDataBean.DataBean.BloodglucoseDataBean.BloodglucoseListBean> churchList;
    private List<String> churchXList;

    private void initChurch(ConnectDataBean.DataBean.BloodglucoseDataBean bloodglucoseData) {
        if (bloodglucoseData != null && bloodglucoseData.getBloodglucoseList() != null && bloodglucoseData.getShowX() != null) {
            ConnectDataBean.DataBean.BloodglucoseDataBean.BloodglucoseListBean bean = bloodglucoseData.getBloodglucoseList().get(0);
            if (bean != null) {
                if (bean.getSugarValue() != null)
                    txBloodChurchValue.setText(bean.getSugarValue());
                if (bean.getTestTime() != null) {
                    String dates = bean.getTestTime();
                    txBloodChurchDate.setText(dates.substring(dates.indexOf("-") + 1, dates.indexOf(" ")).replace("-", "/"));
                    txBloodChurchTime.setText(dates.substring(dates.indexOf(" "), dates.lastIndexOf(":")));
                }
                if (bloodglucoseData.getBloodglucoseList() != null)
                    churchList = bloodglucoseData.getBloodglucoseList();
                churchXList = new ArrayList<>();
                if (churchList != null)
                    for (int x = 0; x < churchList.size(); x++) {
                        if (churchList.get(churchList.size() - 1 - x) != null && churchList.get(churchList.size() - 1 - x).getTestTime() != null) {
                            String date = churchList.get(churchList.size() - 1 - x).getTestTime().replace("-", "/");
                            churchXList.add(date.substring(date.indexOf("/") + 1, date.indexOf(" ")));
                        }
                    }
                if (bean.getGlucoseStatus() != null)
                    Picasso.with(this).load(bean.getGlucoseStatus().equals("偏低") ? R.mipmap.icon_church_1
                            : bean.getGlucoseStatus().equals("正常") ? R.mipmap.icon_church_2
                            : bean.getGlucoseStatus().equals("偏高") ? R.mipmap.icon_church_3
                            : R.mipmap.icon_church_4).into(imageChurchIndex);
                if (churchList != null && churchList != null) {
                    float max = 0;
                    ArrayList<Entry> point = new ArrayList<>();
                    for (int x = 0; x < churchList.size(); x++) {
                        if (churchList.get(x).getSugarValue() != null) {
                            point.add(new Entry((float) x, Float.parseFloat(churchList.get(x).getSugarValue())));
                            max = Float.parseFloat(String.valueOf(churchList.get(x).getSugarValue())) > max ? Float.parseFloat(String.valueOf(churchList.get(x).getSugarValue())) : max;
                        }
                    }
                    if (point.size() > 0 && churchXList.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initSingleLineChart(this, lineChartBloodChurch, point, (ArrayList<String>) churchXList);
                    }
                }
            }
        }
    }

    //设置bmi
    private List<ConnectDataBean.DataBean.BmiDataBean.HeightandweightListBean> bmiList;
    private List<String> bmiXList;

    private void initBmi(ConnectDataBean.DataBean.BmiDataBean bmiData) {
        if (bmiData != null && bmiData.getHeightandweightList() != null && bmiData.getShowX() != null) {
            ConnectDataBean.DataBean.BmiDataBean.HeightandweightListBean bean = bmiData.getHeightandweightList().get(0);
            if (bean != null) {
                if (bean.getHeightValue() != null && bean.getWeightValue() != null)
                    // TODO: 2018/4/12 隐藏身高数据
                    txHeightWeight.setText("--/" + bean.getWeightValue());
                if (bean.getBmi() != null)
                    txBmiValue.setText(bean.getBmi());
                if (bean.getTestTime() != null) {
                    String dates = bean.getTestTime();
                    txBmiDate.setText(dates.substring(dates.indexOf("-") + 1, dates.indexOf(" ")).replace("-", "/"));
                    txBmiTime.setText(dates.substring(dates.indexOf(" "), dates.lastIndexOf(":")));
                }
                if (bmiData.getHeightandweightList() != null)
                    bmiList = bmiData.getHeightandweightList();
                bmiXList = new ArrayList<>();
                if (bmiList != null)
                    for (int x = 0; x < bmiList.size(); x++) {
                        if (bmiList.get(bmiList.size() - 1 - x) != null && bmiList.get(bmiList.size() - 1 - x).getTestTime() != null) {
                            String date = bmiList.get(bmiList.size() - 1 - x).getTestTime().replace("-", "/");
                            bmiXList.add(date.substring(date.indexOf("/") + 1, date.indexOf(" ")));
                        }
                    }
                if (bean.getBmiStatus() != null)
                    Picasso.with(this).load(bean.getBmiStatus().equals("偏瘦") ? R.mipmap.icon_bmi_1
                            : bean.getBmiStatus().equals("正常") ? R.mipmap.icon_bmi_2
                            : bean.getBmiStatus().equals("超重") ? R.mipmap.icon_bmi_3
                            : bean.getBmiStatus().equals("肥胖") ? R.mipmap.icon_bmi_4
                            : bean.getBmiStatus().equals("重度肥胖") ? R.mipmap.icon_bmi_5
                            : R.mipmap.icon_bmi_6).into(imageBmiIndex);
                if (bmiList != null && bmiXList != null) {
                    ArrayList<Entry> point = new ArrayList<>();
                    float max = 0;
                    for (int x = 0; x < bmiList.size(); x++) {
                        if (bmiList.get(x).getBmi() != null) {
                            point.add(new Entry((float) x, Float.parseFloat(bmiList.get(x).getBmi())));
                            max = Float.parseFloat(String.valueOf(bmiList.get(x).getBmi())) > max ? Float.parseFloat(String.valueOf(bmiList.get(x).getBmi())) : max;
                        }
                    }
                    if (point.size() > 0 && bmiXList.size() > 0) {
                        LineChartManager lineChartManager = new LineChartManager();
                        lineChartManager.sety(Float.parseFloat(String.valueOf(((max + 10) / 10) * 10)), 0f);
                        lineChartManager.initSingleLineChart(this, lineChartBmi, point, (ArrayList<String>) bmiXList);
                    }
                }
            }
        }
    }

    private void initBaseInfo(ConnectDataBean.DataBean.UserInfoBean userInfo) {
        if (userInfo != null) {
            txId.setText("ID" + userInfo.getUserId());
            txName.setText("姓名：" + userInfo.getUserName());
            txSex.setText("性别：" + (userInfo.getGender().equals("F") ? "女" : "男"));
            txAge.setText("年龄：" + userInfo.getAge());
            Picasso.with(this).load((userInfo.getGender().equals("F") ? R.mipmap.icon_woman : R.mipmap.icon_man)).into(imageMan);
            txAddress.setText("地址：" + userInfo.getAddress());
            String dates = DateUtil.transferLongToDate("yyyy-MM-dd ", Long.parseLong(userInfo.getCreateTime() + "000"));
            txDate.setText("建档日期：" + dates.replace("-", "."));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketHelper.destory();
    }
}

