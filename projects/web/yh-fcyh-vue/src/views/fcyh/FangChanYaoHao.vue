<template>
    <div style="width:1000px;margin:auto" v-if="tomorrowList.length > 0">
        <el-row>
            <el-card class="box-card">
                <div slot="header" style="color: red;">
                    <span class="entry_name_css">标题</span>
                    <el-button type="primary" size="small" @click="goToPage()" style="float: right">查询</el-button>
                </div>
                <div class="form_item">
                    <el-row><span class="entry_name_right">{{ title }}</span></el-row>
                </div>
            </el-card>
        </el-row>
        <el-row>
            <el-row>
                <el-card class="box-card">
                    <div slot="header" style="color: red;">
                        <span class="entry_name_css">明日接受登记房产列表</span>
                    </div>
                    <div class="form_item">
                        <el-table
                                :data="tomorrowList"
                                stripe
                                border
                                style="width: 100%;margin: 10px 0;font-size: 13px"
                                :header-cell-style="{'text-align':'center','color':'#4371C6','font-size':'14px'}">
                            <el-table-column
                                    prop="entryName"
                                    label="项目名称"
                                    width="140">
                            </el-table-column>
                            <el-table-column
                                    prop="building"
                                    label="楼幢">
                            </el-table-column>
                            <el-table-column
                                    prop="enterpriseName"
                                    label="企业名称">
                            </el-table-column>
                            <el-table-column
                                    prop="region"
                                    label="区域"
                                    width="80">
                            </el-table-column>
                            <el-table-column
                                    prop="startAndEndTime"
                                    label="登记起止时间"
                                    width="160">
                            </el-table-column>
                            <el-table-column
                                    prop="suppliedTotal"
                                    label="供应总套数"
                                    width="100">
                            </el-table-column>
                        </el-table>
                    </div>
                </el-card>
            </el-row>

            <el-row v-if="futureList.length > 0">
                <el-card class="box-card">
                    <div slot="header" style="color: red;">
                        <span class="entry_name_css">未来接受登记房产列表</span>
                    </div>
                    <div class="form_item">
                        <el-table
                                :data="futureList"
                                stripe
                                border
                                style="width: 100%;margin: 10px 0;font-size: 13px"
                                :header-cell-style="{'text-align':'center','color':'#4371C6','font-size':'14px'}">
                            <el-table-column
                                    prop="entryName"
                                    label="项目名称"
                                    width="140">
                            </el-table-column>
                            <el-table-column
                                    prop="building"
                                    label="楼幢">
                            </el-table-column>
                            <el-table-column
                                    prop="enterpriseName"
                                    label="企业名称">
                            </el-table-column>
                            <el-table-column
                                    prop="region"
                                    label="区域"
                                    width="80">
                            </el-table-column>
                            <el-table-column
                                    prop="startAndEndTime"
                                    label="登记起止时间"
                                    width="160">
                            </el-table-column>
                            <el-table-column
                                    prop="suppliedTotal"
                                    label="供应总套数"
                                    width="100">
                            </el-table-column>
                        </el-table>
                    </div>
                </el-card>
            </el-row>
        </el-row>
        <el-row>
            <el-card class="box-card" v-for="item in this.dtlList">
                <div slot="header" style="text-align: center;color: red;">
                    <span class="entry_name_css">{{ item.fgjDtl.entryName }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">开发公司：</span><span class="entry_name_right">{{
                        item.fgjDtl.company
                    }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">所在区域：</span><span class="entry_name_right">{{
                        item.fgjDtl.region
                    }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">楼幢：</span><span class="entry_name_right">{{
                        item.fgjDtl.building
                    }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">供应住房总数：</span><span class="entry_name_right">{{
                        item.fgjDtl.total
                    }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">刚需供应数：</span><span class="entry_name_right">{{
                        item.fgjDtl.needTotal
                    }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">联系电话：</span><span class="entry_name_right">{{
                        item.fgjDtl.phone
                    }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">售楼部地址：</span><span class="entry_name_right">{{
                        item.fgjDtl.salesAddress
                    }}</span>
                </div>
                <div class="form_item">
                    <span class="entry_name_left">登记地址：</span><br><span class="entry_name_right">{{
                        item.fgjDtl.registeredAddress
                    }}</span>
                </div>
                <div class="form_item">
                    <img :src="getImgSrc(item.fgjDtl.erWeiMa)" alt="-" style="width: 100px;height: 100px">
                </div>
                <br>

                <div class="form_item" style="color: red">
                    <span class="entry_name_left">项目均价：</span><span class="entry_name_right">{{
                        item.fgwPage.avgPrice
                    }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">物业类别：</span><span class="entry_name_right">{{
                        item.fgwDtl.wylb
                    }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">建筑类型：</span><span class="entry_name_right">{{
                        item.fgwDtl.jzlx
                    }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">物业公司：</span><span class="entry_name_right">{{
                        item.fgwDtl.wygs
                    }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">容积率：</span><span class="entry_name_right">{{ item.fgwDtl.rjl }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">绿化率：</span><span class="entry_name_right">{{ item.fgwDtl.lhl }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">机动车位配比：</span><span class="entry_name_right">{{
                        item.fgwDtl.jdcw
                    }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">周边配套：</span><span class="entry_name_right">{{
                        item.fgwDtl.zbpt
                    }}</span>
                </div>
                <div class="form_item" style="color: red">
                    <span class="entry_name_left">交通状况：</span><span class="entry_name_right">{{
                        item.fgwDtl.jtzk
                    }}</span>
                </div>
            </el-card>
        </el-row>
        <el-row>
            <el-card class="box-card">
                <div slot="header" style="color: red;">
                    <span class="entry_name_css">摘要</span>
                </div>
                <div class="form_item">
                    <div><span class="entry_name_right">{{ this.summary }}</span></div>
                </div>
                <div class="form_item">
                    <h2>阅读原文</h2>
                    <div><span class="entry_name_right">http://60.173.254.126:8888/</span></div>
                </div>
            </el-card>
        </el-row>
    </div>
    <div v-else style="text-align: center"><h1>暂无数据</h1></div>
</template>

<script>
    import { getMsg } from '@/api/fcyh/fcyh'
    export default {
        name: "FangChanYaoHao",
        data() {
            return {
                title: '',
                summary: '',
                tomorrowList: [],
                futureList: [],
                dtlList: [],
                imgSrc: '../../../assets/img/1.png' //封面
            }
        },
        methods: {
            getMsg() {
                let that = this;
                const loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                getMsg().then(res => {
                    that.title = res.data.title;
                    that.summary = res.data.summary;

                    let arr = res.data.tableMap.tomorrowList;
                    let arr_c = res.data.tableMap.futureList;
                    let arr1 = res.data.dtlList;

                    arr.forEach((item) => {
                        that.tomorrowList.push(item);
                    });
                    arr_c.forEach((item) => {
                        that.futureList.push(item);
                    });
                    arr1.forEach((item) => {
                        that.dtlList.push(item);
                    })
                }).finally(
                    () => {
                        loading.close()
                    }
                )
            },
            getImgSrc(erweima) {
                return erweima;
            },
            goToPage() {
                this.$router.push({path: '/FgwMsgByProjectName'})
            }
        },
        mounted() {
            this.getMsg();
        }
    }
</script>

<style scoped>
    .entry_name_css {
        /*font-weight: bolder;*/
        font-size: 24px;
        color: red;
    }

    .entry_name_left {
        font-weight: bolder;
        font-size: 13px;
    }

    .entry_name_right {
        font-size: 13px;
    }

    .form_item {
        margin-top: 10px;
    }

    .el_row {
        margin-top: 10px
    }
</style>
