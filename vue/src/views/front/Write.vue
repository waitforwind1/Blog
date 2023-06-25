<template>

<div>

  <el-form label-width="80px" size="small" style="margin-top: 20px">
    <el-form-item label="文章标题">
      <el-input v-model="form.name" autocomplete="off" style="width: 80%"></el-input>
      <el-button type="primary" @click="save" icon="el-icon-s-promotion" size="medium" style="margin-left:60px">发布文章</el-button>
    </el-form-item>
    <el-form-item label="文章内容">
      <mavon-editor ref="md" v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
    </el-form-item>
  </el-form>

</div>

</template>
<script>
import axios from "axios";

export default {
  name: "write",
  data() {
    return {
      dialogFormVisible: false,
      form:{},
      input:'我是zz',
      from: {},//表单
      context: '',//输入的数据
      html: '',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  methods: {
    // handleEditorImgAdd (pos, $file) {   //添加图片
    //   let formdata = new FormData()
    //   formdata.append('file', $file)
    //   this.imgFile[pos] = $file
    //   let instance = this.$axios.create({
    //     withCredentials: true,
    //     headers: {
    //       Authorization: token   // 我上传的时候请求头需要带上token 验证，不需要的删除就好
    //     }
    //   })
    //   instance.post('/api/upload/fileds', formdata).then(res => {
    //     if (res.data.code === 200) {
    //       this.$Message.success('上传成功')
    //       let url = res.data.data
    //       let name = $file.name
    //       if (name.includes('-')) {
    //         name = name.replace(/-/g, '')
    //       }
    //       let content = this.form.content
    //       // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)  这里是必须要有的
    //       if (content.includes(name)) {
    //         let oStr = `(${pos})`
    //         let nStr = `(${url})`
    //         let index = content.indexOf(oStr)
    //         let str = content.replace(oStr, '')
    //         let insertStr = (soure, start, newStr) => {
    //           return soure.slice(0, start) + newStr + soure.slice(start)
    //         }
    //         this.form.content = insertStr(str, index, nStr)
    //       }
    //     } else {
    //       this.$Message.error(res.data.msg)
    //     }
    //   })
    // },
    // handleEditorImgDel (pos) {   //删除图片
    //   delete this.imgFile[pos]
    // },
    imgAdd(pos, $file) {
      let $vm = this.$refs.md
      // 第一步.将图片上传到服务器.
      const formData = new FormData();
      formData.append('file', $file);
      axios({
        url: 'http://localhost:9090/file/upload',
        method: 'post',
        data: formData,
        headers: {'Content-Type': 'multipart/form-data'},
      }).then((res) => {
        // 第二步.将返回的url替换到文本原位置![...](./0) -> ![...](url)
        $vm.$img2Url(pos, res.data);
      })
    },
    save() {
      console.log(this.from.content)
      if(!this.user.id)
        this.$message.warning("请登陆后操作")
      else if(!this.form.content)
        {
          this.$message.warning("发布内容不准为空")
        }
      else {
        this.request.post("/article", this.form).then(res => {
          if (res.code === '200') {
            this.$message.success("保存成功")
            this.dialogFormVisible = false
            this.load()
          } else {
            this.$message.error("保存失败")
          }
        })
      }
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    // 编辑区域内容改变
    changeFn(value, render) {
      // console.log(value, render)
      this.html = render
    },
    // 保存的时候的事件
    saveFn(e) {
      console.log(e)
    },
    // 添加上传图片事件
    imgAddFn(filename, imgfile) {
      console.log(filename, imgfile)
      // 第一步.将图片上传到服务器.
      var formdata = new FormData();
      formdata.append('image', $file);
      axios({
        url: 'server url',
        method: 'post',
        data: formdata,
        headers: { 'Content-Type': 'multipart/form-data' },
      }).then((url) => {
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        /**
         * $vm 指为mavonEditor实例，可以通过如下两种方式获取
         * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
         * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
         */
        $vm.$img2Url(pos, url);
      })
    }
  }
}
</script>
