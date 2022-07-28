<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Modify'"
    :close-on-click-modal="false"
    :visible.sync="visible"
    @closed="dialogClose"
  >
    <el-form
      :model="dataForm"
      :rules="dataRule"
      ref="dataForm"
      @keyup.enter.native="dataFormSubmit()"
      label-width="120px"
    >
      <el-form-item label="group name" prop="attrGroupName">
        <el-input v-model="dataForm.attrGroupName" placeholder="group name"></el-input>
      </el-form-item>
      <el-form-item label="sort" prop="sort">
        <el-input v-model="dataForm.sort" placeholder="sort"></el-input>
      </el-form-item>
      <el-form-item label="description" prop="descript">
        <el-input v-model="dataForm.descript" placeholder="description"></el-input>
      </el-form-item>
      <el-form-item label="group icon" prop="icon">
        <el-input v-model="dataForm.icon" placeholder="group icon"></el-input>
      </el-form-item>
      <el-form-item label="Category" prop="catelogId">
        <category-cascader :catelogPath.sync="catelogPath"></category-cascader>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">Cancel</el-button>
      <el-button type="primary" @click="dataFormSubmit()">OK</el-button>
    </span>
  </el-dialog>
</template>

<script>
import CategoryCascader from "../common/category-cascader";
export default {
  data() {
    return {
      props: {
        value: "catId",
        label: "name",
        children: "children"
      },
      visible: false,
      categories: [],
      catelogPath: [],
      dataForm: {
        attrGroupId: 0,
        attrGroupName: "",
        sort: "",
        description: "",
        icon: "",
        catelogId: 0
      },
      dataRule: {
        attrGroupName: [
          { required: true, message: "Group name cannot be empty", trigger: "blur" }
        ],
        sort: [{ required: true, message: "Sort cannot be empty", trigger: "blur" }],
        description: [
          { required: true, message: "Description cannot be empty", trigger: "blur" }
        ],
        icon: [{ required: true, message: "Group icon cannot be empty", trigger: "blur" }],
        catelogId: [
          { required: true, message: "The category id cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  components: { CategoryCascader },

  methods: {
    dialogClose() {
      this.catelogPath = [];
    },
    getCategorys() {
      this.$http({
        url: this.$http.adornUrl("/product/category/list/tree"),
        method: "get"
      }).then(({ data }) => {
        this.categorys = data.page;
      });
    },
    init(id) {
      this.dataForm.attrGroupId = id || 0;
      this.visible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].resetFields();
        if (this.dataForm.attrGroupId) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/attrgroup/info/${this.dataForm.attrGroupId}`
            ),
            method: "get",
            params: this.$http.adornParams()
          }).then(({ data }) => {
            console.log(data)
            console.log(data)
            if (data && data.code === 0) {
              this.dataForm.attrGroupName = data.attrGroup.attrGroupName;
              this.dataForm.sort = data.attrGroup.sort;
              this.dataForm.descript = data.attrGroup.descript;
              this.dataForm.icon = data.attrGroup.icon;
              this.dataForm.catelogId = data.attrGroup.catelogId;
              // Find out the full path of catelogId
              this.catelogPath = data.attrGroup.catelogPath;
            }
            
          });
        }
      });
    },
    // form submit
    dataFormSubmit() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(
              `/product/attrgroup/${
                !this.dataForm.attrGroupId ? "save" : "update"
              }`
            ),
            method: "post",
            data: this.$http.adornData({
              attrGroupId: this.dataForm.attrGroupId || undefined,
              attrGroupName: this.dataForm.attrGroupName,
              sort: this.dataForm.sort,
              descript: this.dataForm.descript,
              icon: this.dataForm.icon,
              catelogId: this.catelogPath[this.catelogPath.length - 1]
            })
          }).then(({ data }) => {
            if (data && data.code === 0) {
              this.$message({
                message: "Operation successful",
                type: "success",
                duration: 1500,
                onClose: () => {
                  this.visible = false;
                  this.$emit("refreshDataList");
                }
              });
            } else {
              this.$message.error(data.msg);
            }
          });
        }
      });
    }
  },
  created() {
    this.getCategorys();
  }
};
</script>
