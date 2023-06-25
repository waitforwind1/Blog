<template>
  <div style="color: #666">
    <div style="margin: 20px 0; ">
      <div class="pd-10" style="font-size: 20px; color: #3F5EFB; cursor: pointer">{{ article.name }}</div>
      <div style="font-size: 14px; margin-top: 10px">
        <i class="el-icon-user-solid"></i> <span>{{ article.user }}</span>
        <i class="el-icon-time" style="margin-left: 10px"></i> <span>{{ article.time }}</span>
      </div>
    </div>

   <div style="margin: 20px 0">
     <mavon-editor
         class="md"
         :value="article.content"
         :subfield="false"
         :defaultOpen="'preview'"
         :toolbarsFlag="false"
         :editable="false"
         :scrollStyle="true"
         :ishljs="true"
     />
   </div>
  <div>
    <div class="el-button"  style="position:absolute;right: 338px;margin-bottom:30px;padding: 0;">
      <el-button   type="text" style="width:50px" icon="iconfont icon-dianzan" @click="dianzan" v-if="is_like" ></el-button> <!--点赞-->
      <el-button   type="text" style="width:50px" icon="iconfont icon-dianzan1" @click="cancleLike" v-if="!is_like" ></el-button>
    </div>

    <div class="el-button"  style="position:absolute;right: 268px;margin-bottom:30px;padding: 0;" >
      <el-button    type="text" style="width:50px" icon="iconfont icon-shoucang" @click="collect" v-if="is_collect"></el-button><!--收藏-->
      <el-button    type="text" style="width:50px" icon="iconfont icon-shoucang_shixin" @click="cancleCol" v-if="!is_collect"></el-button>
    </div>
  </div>

    <div style="margin: 30px 0">
      <div style="margin: 10px 0">
        <div style="border-bottom: 1px solid orangered; padding: 10px 0; font-size: 20px">评论</div>
        <div style="padding: 10px 0">
          <el-input size="small" type="textarea" v-model="commentForm.content"></el-input>
        </div>
        <div class="pd-10" style="text-align: right">
          <el-button type="primary" size="small" @click="save">评论</el-button>
        </div>
      </div>

<!--      评论列表-->
      <div>
        <div v-for="item in comments" :key="item.id" style="border-bottom: 1px solid #ccc; padding: 10px 0; ">
          <div style="display: flex">
            <div style="width: 100px; text-align: center">
              <el-image :src="item.avatarUrl" style="width: 50px; height: 50px; border-radius: 50%"></el-image>
            </div> <!--  头像-->
            <div style="flex: 1; font-size: 14px; padding: 5px 0; line-height: 25px">
              <b>{{ item.nickname }}：</b>
              <span>{{ item.content }}</span>

              <div style="display: flex; line-height: 20px; margin-top: 5px">
                <div style="width: 200px;">
                  <i class="el-icon-time"></i><span style="margin-left: 5px">{{ item.time }}</span>
                </div>
                <div style="text-align: right; flex: 1">
                  <el-button style="margin-left: 5px" type="text" @click="handleReply(item.id)">回复</el-button>
                  <el-button type="text" style="color: red" @click="del(item.id)" v-if="user.id === item.userId">删除</el-button>
                </div>
              </div>
            </div>   <!--  内容-->
          </div>

          <div v-if="item.children.length"  style="padding-left: 200px;">
            <div v-for="subItem in item.children" :key="subItem.id"  style="background-color: #f0f0f0; padding: 5px 20px">
              <!--          回复列表-->
              <div style="font-size: 14px; padding: 5px 0; line-height: 25px">

                <div>
                  <b style="color: #3a8ee6" v-if="subItem.pnickname">@{{ subItem.pnickname }}</b>
                </div>

                <div style=" padding-left: 5px">
                  <el-image :src="subItem.avatarUrl" style="width: 30px; height: 30px; border-radius: 50%"></el-image>
                  <b style="position: absolute;margin-top: 5px">{{ subItem.nickname }}：{{ subItem.content }}</b>
                </div>

                <div style="display: flex; line-height: 20px; margin-top: 5px; padding-left: 5px">
                  <div style="width: 200px;">
                    <i class="el-icon-time"></i><span style="margin-left: 5px">{{ subItem.time }}</span>
                  </div>
                  <div style="text-align: right; flex: 1">
                    <el-button style="margin-left: 5px" type="text" @click="handleReply(subItem.id)">回复</el-button>
                    <el-button type="text" style="color: red" @click="del(subItem.id)" v-if="user.id === subItem.userId">删除</el-button>
                  </div>
                </div>
              </div>   <!--  内容-->
            </div>

          </div>


        </div>
      </div>
    </div>


    <el-dialog title="回复" :visible.sync="dialogFormVisible" width="50%" >
      <el-form label-width="80px" size="small">
        <el-form-item label="回复内容">
          <el-input type="textarea" v-model="commentForm.contentReply" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false" size="small">取 消</el-button>
        <el-button type="primary" @click="save"  size="small">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  inject:['reload'],
  name: "Article",
  data() {
    return {
      is_like: 'true',
      collectionIds: {},
      likeIds: {},
      collectionState: false,
      is_collect: 'true',
      article: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      comments: [],
      commentForm: {},
      id: this.$route.query.id,
      dialogFormVisible: false
    }
  },
  created() {
    this.load()
    this.loadComment()
    this.checkCollect()
    this.checkLike()
  },
  methods: {
    load() {
      this.request.get("/article/" + this.id).then(res => {
        this.article = res.data
      })
    },
    loadComment() {
      this.request.get("/comment/tree/" + this.id).then(res => {
        this.comments = res.data
      })
    },
    save() {
      if (!this.user.id) {
        this.$message.warning("请登录后操作")
        return
      } else if (!this.commentForm.content && !this.commentForm.contentReply) {
        this.$message.warning("评论内容不可为空")
        return
      }
      this.commentForm.articleId = this.id
      if (this.commentForm.contentReply) {
        this.commentForm.content = this.commentForm.contentReply
      }
      this.request.post("/comment", this.commentForm).then(res => {
        if (res.code === '200') {
          this.$message.success("评论成功")
          this.commentForm = {}  // 初始化评论对象内容
          this.loadComment()
          this.dialogFormVisible = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    del(id) {
      this.request.delete("/comment/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.loadComment()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleReply(pid) {
      this.commentForm = {pid: pid}
      this.dialogFormVisible = true
    },
    cancleLike() {
      this.$confirm('确定取消点赞？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.likeIds.islike = 0
        this.request.get("/article/like/cancleLike/"+this.id).then(res => {
          if (res.data.islike == 0) {
            this.is_like = false
            this.$message.success("取消点赞成功")
            location. reload()
            this.$router.go(0)
          }
        })
      })
    },
    cancleCol(){
      this.$confirm('确定取消收藏？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.likeIds.iscollect = 0
        this.request.get("/article/collect/cancleCol/"+this.id).then(res => {
          if (res.data.iscollect == 0) {
            this.is_collect = false
            this.$message.success("取消成功")
            this.load()
          }
        })
      })
    },
    checkCollect() {
      this.request.get("/article/collect/check/" + this.id).then(res => {
        if (res.data.iscollect == 1)
          this.is_collect = false
      })
    },
    checkLike() {
      this.request.get("/article/like/" + this.id).then(res => {
        if (res.data.islike == 1)
          this.is_like = false
      })
    },
    collect() {
      if (!this.user.id) {
        this.$message.warning("请登录后操作")
        return
      }
      this.collectionIds.iscollect=1
      this.collectionIds.blogname = this.article.name//文章标题
      this.collectionIds.username = this.article.user//文章作者
      this.collectionIds.blogId = this.id //当前文章ID
      this.collectionIds.userId = this.user.id //当前用户ID
      this.request.post("/article/collect/addCollect", this.collectionIds).then(res => {
        if (res) {
          this.$message.success("收藏成功")
          this.is_collect = false
        }
        // else if()
        else {
          this.$message.warning("收藏失败")
        }
      })
    },
    dianzan() {
      if (!this.user.id) {
        this.$message.warning("请登录后操作")
        return
      }
      this.likeIds.bid = this.id
      this.likeIds.uid = this.user.id
      this.likeIds.islike = 1
      this.request.post("/article/like/addLike", this.likeIds).then(res => {
        console.log(res)
        if (res.data) {
          this.$message.success("点赞成功")
          this.is_like = false
        } else {
          this.$message.warning("系统错误")
        }
      })

    },
  }
}
</script>

<style scoped>

</style>
