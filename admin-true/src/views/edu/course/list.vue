<template>
  <div class="app-container">
    <h2>课程列表</h2>
    <br />
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称" />
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="courseQuery.status"
          clearable
          placeholder="课程状态"
        >
          <el-option value="Normal" label="已发布" />
          <el-option value="Draft" label="未发布" />
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()"
        >查询</el-button
      >
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格  {{ scope.$index + 1 }}获得当前行号-->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="课程名称" width="180" />
      <el-table-column label="课程状态" width="80">
        <template slot-scope="scope">
          {{ scope.row.status === "Normal" ? "已发布" : "未发布" }}
        </template>
      </el-table-column>
      <el-table-column prop="lessonNum" label="课时数" width="80" />
      <el-table-column prop="gmtCreate" label="添加时间" width="160" />
      <el-table-column prop="viewCount" label="浏览数量" width="60" />
      <el-table-column label="操作" align="center">
        <!-- 对每一行的3种操作 -->
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="getCourseInfoId(scope.row.id)"
            icon="el-icon-edit"
            >编辑课程信息</el-button
          >
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="editStruct(scope.row.id)"
            >编辑大纲信息</el-button
          >
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
            >删除课程信息</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑课程基本信息"
      :visible.sync="dialogFormVisible"
      width="70%"
    >
      <el-form label-width="120px">
        <el-form-item label="课程标题">
          <el-input
            v-model="courseInfo.title"
            placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
          />
        </el-form-item>
        <!-- 所属分类 TODO -->
        <el-form-item label="课程分类">
          <el-select
            v-model="courseInfo.subjectParentId"
            placeholder="一级分类"
            @change="subjectLevelOneChanged"
          >
            <el-option
              v-for="subject in subjectOneList"
              :key="subject.id"
              :label="subject.title"
              :value="subject.id"
            />
          </el-select>
          <!-- 二级分类 -->
          <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
            <el-option
              v-for="subject in subjectTwoList"
              :key="subject.id"
              :label="subject.title"
              :value="subject.id"
            />
          </el-select>
        </el-form-item>
        <!-- 课程讲师 TODO -->
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
          <el-select v-model="courseInfo.teacherId" placeholder="请选择">
            <el-option
              v-for="teacher in teacherList"
              :key="teacher.id"
              :label="teacher.name"
              :value="teacher.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="总课时">
          <el-input-number
            :min="0"
            v-model="courseInfo.lessonNum"
            controls-position="right"
            placeholder="请填写课程的总课时数"
          />
        </el-form-item>
        <!-- 课程简介 TODO -->
        <!-- 课程简介-->
        <el-form-item label="课程简介">
          <tinymce :height="300" v-model="courseInfo.description" ref="desc" />
        </el-form-item>
        <!-- 课程封面 TODO -->
        <!-- 课程封面-->
        <el-form-item label="课程封面">
          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API + '/eduoss/fileoss'"
            class="avatar-uploader"
          >
            <img :src="courseInfo.cover" id="fengmian"/>
          </el-upload>
        </el-form-item>
        <el-form-item label="课程价格">
          <el-input-number
            :min="0"
            v-model="courseInfo.price"
            controls-position="right"
            placeholder="免费课程请设置为0元"
          />
          元
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateCourse">保 存</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="编辑课程大纲信息"
      :visible.sync="structVisible"
      width="70%"
    >
      <el-button type="primary" round @click="openChapterDialog()"
        >添加章节</el-button
      >
      <!-- 章节 -->
      <ul class="chanpterList">
        <li v-for="chapter in chapterVideoList" :key="chapter.id">
          <p>
            {{ chapter.title }}
            <span class="acts">
              <el-button style="" type="text" @click="openVideo(chapter.id)"
                >添加小节</el-button
              >
              <el-button
                style=""
                type="text"
                @click="openEditChatper(chapter.id)"
                >编辑</el-button
              >
              <el-button type="text" @click="removeChapter(chapter.id)"
                >删除</el-button
              >
            </span>
          </p>
          <!-- 视频?? 小节 -->
          <ul class="chanpterList videoList">
            <li v-for="video in chapter.children" :key="video.id">
              <p>
                {{ video.title }}
                <span class="acts">
                  <!-- 编辑小节 -->
                  <el-button
                    style=""
                    type="text"
                    @click="openEditVideo(video.id)"
                    >编辑</el-button
                  >
                  <el-button type="text" @click="removeVideo(video.id)"
                    >删除</el-button
                  >
                </span>
              </p>
            </li>
          </ul>
        </li>
      </ul>
    </el-dialog>
    <!-- 添加和修改章节的弹窗 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加/修改章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number
            v-model="chapter.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改小节 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加/修改课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="1">免费</el-radio>
            <el-radio :label="0">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            v-model="video.videoOriginalName"
            :on-success="handleVodUploadSuccess"
            :file-list="fileList"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :action="BASE_API + '/eduvod/video'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G,<br />
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br />
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br />
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
//引入调用teacher.js文件
import course from "@/api/edu/course";
import subject from "@/api/edu/subject";
import Tinymce from "@/components/Tinymce";
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";
export default {
  components: { Tinymce },
  //写核心代码位置
  // data:{
  // },
  data() {
    //定义变量和初始值
    return {
      form: {
        name: "",
        region: ""
      },
      structVisible: false, //编辑大纲弹窗
      teacherList: [],
      courseInfo: {
        title: "",
        subjectId: "", //二级分类id
        subjectParentId: "", //一级分类id
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover: "/static/01.png",
        price: 0
      },
      fileList: [], //上传文件列表
      subjectOneList: [],
      subjectTwoList: [],
      BASE_API: process.env.BASE_API,
      dialogFormVisible: false,
      list: null, //查询之后接口返回集合
      page: 1, //
      limit: 10, //每页记录数
      total: 0, //总记录数
      courseQuery: {}, //条件封装对象
      //编辑课程大纲所需的data
      courseId: "", //课程id
      chapterVideoList: [],
      chapter: {
        //封装章节数据
        title: "",
        sort: 0
      },
      video: {
        title: "",
        sort: 1,
        isFree: 1,
        videoSourceId: "",
        courseId: "",
        chapterId: "",
        videoOriginalName: ""
      },
      dialogChapterFormVisible: false, //章节弹框
      dialogVideoFormVisible: false //小节弹框
    };
  },
  created() {
    //页面渲染之前执行，一般调用methods定义的方法
    //调用
    this.getList();
  },
  methods: {
    handleVodUploadSuccess(response, file, fileList) {
      console.log("handleVodUploadSuccess");
      this.video.videoSourceId = response.data.videoId;
      this.video.videoOriginalName = file.name;
    },
    uploadVideo(event) {
      console.log("true");
      let file = event.target.files[0];
      let form = new FormData();
      form.append("file", file);
      video.uploadFile(form).then(() => {
        console.log("11");
      });
    },
    handleUploadExceed() {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },
    //删除视频
    handleVodRemove() {
      video.deleteAliyunvod(this.video.videoSourceId).then(response => {
        this.$message({
          type: "success",
          message: "删除视频成功!"
        });
        this.fileList = [];
        this.video.videoSourceId = "";
        this.video.videoOriginalName = "";
      });
    },
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定删除 ${file.name}吗？`);
    },
    //查询课程列表
    getList(page = 1) {
      this.page = page;
      course
        .getListCourse(this.page, this.limit, this.courseQuery)
        .then(response => {
          //请求成功
          //response接口返回的数据
          //console.log(response)
          this.list = response.data.rows;
          this.total = response.data.total;
          console.log(this.list);
          console.log(this.total);
        });
    },
    //更新课程基本信息
    updateCourse() {
      this.dialogFormVisible = false;
      course.updateCourseInfo(this.courseInfo).then(response => {
        //提示
        this.$message({
          type: "success",
          message: "修改课程信息成功!"
        });
      });
      this.getList();
      this.courseInfo = {};
    },
    //处理课程封面上传
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url;
    },
    //上传之前调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg" || "image/png";
      const isLt2M = file.size / 1024 / 1024 < 4;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过4MB!");
      }
      return isJPG && isLt2M;
    },
    //回滚选定的课程 以进行修改
    getCourseInfoId(a) {
      this.courseId = a;
      this.dialogFormVisible = true;
      course.getCourseInfoId(this.courseId).then(response => {
        //在courseInfo课程基本信息，包含 一级分类id 和 二级分类id
        this.courseInfo = response.data.courseInfoVo;
        this.$refs.desc.setContent(this.courseInfo.description);
        //1 查询所有的分类，包含一级和二级
        subject.getSubjectList().then(response => {
          //2 获取所有一级分类
          this.subjectOneList = response.data.list;
          //3 把所有的一级分类数组进行遍历，
          for (var i = 0; i < this.subjectOneList.length; i++) {
            //获取每个一级分类
            var oneSubject = this.subjectOneList[i];
            //比较当前courseInfo里面一级分类id和所有的一级分类id
            if (this.courseInfo.subjectParentId == oneSubject.id) {
              //获取一级分类所有的二级分类
              this.subjectTwoList = oneSubject.children;
            }
          }
        });
        //初始化所有讲师
        this.getListTeacher();
      });
    },
    //处理回滚dialog中的学科分类下拉选项
    getOneSubject() {
      subject.getSubjectList().then(response => {
        this.subjectOneList = response.data.list;
      });
    },
    //处理dialog中下拉教师选择
    getListTeacher() {
      course.getListTeacher().then(response => {
        this.teacherList = response.data.items;
      });
    },
    //动态改变学科分类一二级
    subjectLevelOneChanged(value) {
      //value就是一级分类id值
      //遍历所有的分类，包含一级和二级
      for (var i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        var oneSubject = this.subjectOneList[i];
        //判断：所有一级分类id 和 点击一级分类id是否一样
        if (value === oneSubject.id) {
          //从一级分类获取里面所有的二级分类
          this.subjectTwoList = oneSubject.children;
          //把二级分类id值清空
          this.courseInfo.subjectId = "";
        }
      }
    },
    //删除某条记录
    removeDataById(id) {
      console.log("准备删除该课程");
      course.removeDataById(id).then(response => {
        this.$message({
          type: "success",
          message: "课程删除成功!"
        });
        this.getList();
      });
      this.getList();
    },
    resetData() {
      //清空的方法
      //表单输入项数据清空
      this.courseQuery = {};
      //查询所有讲师数据
      this.getList();
    },
    editStruct(aaa) {
      this.structVisible = true;
      this.courseId = aaa;
      //根据课程id查询章节和小节
      this.getChapterVideo();
    },
    getChapterVideo() {
      chapter.getAllChapterVideo(this.courseId).then(response => {
        this.chapterVideoList = response.data.allChapterVideo;
      });
      console.log(this.chapterVideoList);
    },
    openEditVideo(aa) {
      console.log(this.video);
      console.log("回滚小节:" + aa);
      //弹框
      this.dialogVideoFormVisible = true;
      video.getVideo(aa).then(response => {
        this.video = response.data.video;
        this.video.isFree = response.data.video.isFree ? 1 : 0;
        if (response.data.video.videoOriginalName) {
          let temp = [];
          temp.push(response.data.video.videoOriginalName);
          console.log(response.data.video.videoOriginalName);
          this.fileList = temp.map(item => {
            return {
              name: item,
              url: item
            };
          });
        }
      });
    },
    saveOrUpdate() {
      console.log(this.chapter);
      //如果是回滚的话 会先创建chapter再保存 会产生chapter.id
      if (!this.chapter.id) {
        this.addChapter();
      } else {
        this.updateChapter();
      }
    },
    //更新小节
    updateVideo() {
      video.updateVideo(this.video).then(response => {
        //关闭弹框
        this.dialogVideoFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "更新小节成功!"
        });
        //刷新页面
        this.getChapterVideo();
      });
    },
    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.addVideo();
      } else this.updateVideo();
    },
    //删除小节
    removeVideo(id) {
      this.$confirm("此操作将删除小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        //点击确定，执行then方法
        //调用删除的方法
        video.deleteVideo(id).then(response => {
          //删除成功
          //提示信息
          this.$message({
            type: "success",
            message: "删除小节成功!"
          });
          //刷新页面
          this.getChapterVideo();
        });
      }); //点击取消，执行catch方法
    },
    //添加小节弹框的方法
    openVideo(chapterId) {
      console.log("打开添加小节的弹框");
      this.video = {};
      //弹框
      this.dialogVideoFormVisible = true;
      //设置章节id 在video属性中加入chapterId 证明是属于哪个章节下
      this.video.chapterId = chapterId;
      console.log(this.video);
    },
    //添加小节
    addVideo() {
      //设置课程id
      this.video.courseId = this.courseId;
      console.log("添加小节：");
      console.log(this.video);
      video.addVideo(this.video).then(response => {
        //关闭弹框
        this.dialogVideoFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "添加小节成功!"
        });
        //刷新页面
        this.getChapterVideo();
      });
      //添加过后清空表格数据
      this.video = {};
    },
    //==============================章节操作====================================
    //删除章节
    removeChapter(chapterId) {
      this.$confirm("此操作将删除章节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        //点击确定，执行then方法
        //调用删除的方法
        chapter.deleteChapter(chapterId).then(response => {
          //删除成功
          //提示信息
          this.$message({
            type: "success",
            message: "删除成功!"
          });
          //刷新页面
          this.getChapterVideo();
        });
      }); //点击取消，执行catch方法
    },
    //修改大章节 弹框数据回显
    openEditChatper(chapterId) {
      //弹框
      this.dialogChapterFormVisible = true;
      //调用接口
      chapter.getChapter(chapterId).then(response => {
        this.chapter = response.data.chapter;
      });
    },
    //弹出添加章节页面
    openChapterDialog() {
      //弹框
      this.dialogChapterFormVisible = true;
      //添加章节的表单数据清空
      this.chapter = {};
    },
    //添加章节
    addChapter() {
      //设置课程id到chapter对象里面
      this.chapter.courseId = this.courseId;
      chapter.addChapter(this.chapter).then(response => {
        //关闭弹框
        this.dialogChapterFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "添加章节成功!"
        });
        //刷新页面
        this.getChapterVideo();
      });
    },
    //修改章节的方法
    updateChapter() {
      chapter.updateChapter(this.chapter).then(response => {
        //关闭弹框
        this.dialogChapterFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "修改章节成功!"
        });
        //刷新页面
        this.getChapterVideo();
      });
    }
  }
};
</script>
<style>
#fengmian{
  width:80%
}
</style>