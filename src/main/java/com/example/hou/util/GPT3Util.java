package com.example.hou.util;



import java.time.Duration;
import java.util.List;

/**
 * @program: juanBao
 * @description:
 * @author: 作者
 * @create: 2023-12-30 22:45
 *
 * GPT 的token
 * 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

国内直接访问Api URL会超时, 这里的解决方法是使用Cloudflare 的 Workers 来代理 OpenAI 的 API 地址，
配合自己的域名即可在境内实现访问。因为 Cloudflare Workers 有每天免费 10 万次的请求额度，也有可以免费注册的域名，
所以几乎可以说是零经济成本。教程自行查询


 翻墙域名问题  用国内的百度
46076637    Ov7A87aDXw1Fbf0Zp6DOE2s2    kXNj66TYV2zYnKOTaKXL8nGDWW22OEQd
AppID	API Key                              Secret Key


{
"refresh_token": "25.40ba453e7207c4142e38740b69375ce9.315360000.2019316460.282335-46076637",
"expires_in": 2592000,       这个
"session_key": "9mzdWW3Xn3smqNwdWgpDhWJp5UnltmPof166EQkb6XWU1XnBVRCF6pai2HTQQ6W0dfsvNVoxUidepOiX5trHzCchsgejLg==",
"access_token": "24.b7d5fc25054f9c89d57bd30478d2acfd.2592000.1706548460.282335-46076637",      这个   有效期一个月  2023.1.31失效
"scope": "public brain_all_scope ai_custom_yiyan_com ai_custom_yiyan_com_eb_instant wenxinworkshop_mgr ai_custom_yiyan_com_bloomz7b1 ai_custom_yiyan_com_emb_text ai_custom_yiyan_com_llama2_7b ai_custom_yiyan_com_llama2_13b ai_custom_yiyan_com_llama2_70b ai_custom_yiyan_com_chatglm2_6b_32k ai_custom_yiyan_com_aquilachat_7b ai_custom_yiyan_com_emb_bge_large_zh ai_custom_yiyan_com_emb_bge_large_en ai_custom_yiyan_com_qianfan_chinese_llama_2_7b ai_custom_qianfan_bloomz_7b_compressed ai_custom_yiyan_com_eb_pro ai_custom_yiyan_com_sd_xl ai_custom_yiyan_com_8k ai_custom_yiyan_com_ai_apaas ai_custom_yiyan_com_qf_chinese_llama_2_13b ai_custom_yiyan_com_sqlcoder_7b ai_custom_yiyan_com_codellama_7b_ins ai_custom_yiyan_com_xuanyuan_70b_chat ai_custom_yiyan_com_yi_34b ai_custom_yiyan_com_chatlaw ai_custom_yiyan_com_emb_tao_8k wise_adapt lebo_resource_base lightservice_public hetu_basic lightcms_map_poi kaidian_kaidian ApsMisTest_Test权限 vis-classify_flower lpq_开放 cop_helloScope ApsMis_fangdi_permission smartapp_snsapi_base smartapp_mapp_dev_manage iop_autocar oauth_tp_app smartapp_smart_game_openapi oauth_sessionkey smartapp_swanid_verify smartapp_opensource_openapi smartapp_opensource_recapi fake_face_detect_开放Scope vis-ocr_虚拟人物助理 idl-video_虚拟人物助理 smartapp_component smartapp_search_plugin avatar_video_test b2b_tp_openapi b2b_tp_openapi_online smartapp_gov_aladin_to_xcx",
"session_secret": "3fb6fa9a85d20255c1f1f7f25a8bdbaa"
}
 */
public class GPT3Util {


    public String chat(String prompt){

        return "暂时先不写";

    }


}
