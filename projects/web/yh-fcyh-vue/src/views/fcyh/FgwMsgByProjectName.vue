<template>
    <div style="width:1000px;margin:auto">
        <el-row style="margin-top: 20px">
            <el-input v-model="txtspname" placeholder="请输入内容" size="small" style="width: 200px"></el-input>
            <el-button size="small" type="primary" @click="queryFgw()" style="margin-left: 10px">查询</el-button>
        </el-row>
        <el-row style="margin-top: 20px">
            <el-card class="box-card">
                <div slot="header" style="text-align: center;color: red;">
                    <span class="entry_name_css">{{ fgwPage.projectName }}</span>
                </div>

                <div class="form_item" >
                    <span class="entry_name_left">项目均价：</span><span class="entry_name_right">{{
                        fgwPage.avgPrice
                    }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">物业类别：</span><span class="entry_name_right">{{ fgwDtl.wylb }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">建筑类型：</span><span class="entry_name_right">{{ fgwDtl.jzlx }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">物业公司：</span><span class="entry_name_right">{{ fgwDtl.wygs }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">容积率：</span><span class="entry_name_right">{{ fgwDtl.rjl }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">绿化率：</span><span class="entry_name_right">{{ fgwDtl.lhl }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">机动车位配比：</span><span class="entry_name_right">{{
                        fgwDtl.jdcw
                    }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">周边配套：</span><span class="entry_name_right">{{ fgwDtl.zbpt }}</span>
                </div>
                <div class="form_item" >
                    <span class="entry_name_left">交通状况：</span><span class="entry_name_right">{{ fgwDtl.jtzk }}</span>
                </div>
            </el-card>
        </el-row>
    </div>

</template>

<script>
    import { queryFgw } from '@/api/fcyh/fcyh'
    export default {
        name: "FgwMsgByProjectName",
        data() {
            return {
                txtspname: '',
                fgwPage: {},
                fgwDtl: {},
            }
        },
        methods: {
            queryFgw() {
                let that = this;
                const loading = this.$loading({
                    lock: true,
                    text: 'Loading',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                queryFgw({'txtspname': this.txtspname}).then(res=>{
                    that.fgwPage = res.data.fgwPage;
                    that.fgwDtl = res.data.fgwDtl;
                }).finally(()=>{
                    loading.close()
                })

            }
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
