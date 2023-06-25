<template>
<div>
        <el-table border stripe :data="tableData" :header-cell-class-name="'headerBg'"  >
          <el-table-column prop="id" label="ID" width="100px"></el-table-column>
          <el-table-column prop="name" label="名称" width="180px"></el-table-column>
          <el-table-column prop="size" label="尺寸" width="180px">
            <template slot-scope="scope">
              {{scope.row.size}}KB
            </template>
          </el-table-column>
          <el-table-column prop="type" label="类型" width="180px"></el-table-column>
          <el-table-column prop="url" label="照片样式" width="180px">
            <template v-slot="scope">
              <img :src="scope.row.url" alt="" width="80" height="80">
            </template>
          </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
          <el-pagination
                background
                @current-change="handleCurrentChange"
                layout="prev, pager, next"
                :total="60">
          </el-pagination>
        </div>

  </div>

</template>

<script>
export default {
  name: "File",
  data(){
    return{
      tableData:[],
      form:{},
      pageNum: 1,
      pageSize: 5,
      total:0,
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/file/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res=>{
        this.tableData=res.data.records
        this.total=res.data.total
        // console.log("sizeaaa"+this.pageSize)
        console.log("总共"+this.total)
        console.log(res)
      })
    },
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    }
  },
}
</script>

<style scoped>
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
.headerBg {
  background: #eee!important;
}
</style>