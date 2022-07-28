<template>
  <div>
    <el-dialog :close-on-click-modal="false" :visible.sync="visible" @closed="dialogClose">
      <el-dialog width="40%" title="Select attribute" :visible.sync="innerVisible" append-to-body>
        <div>
          <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
            <el-form-item>
              <el-input v-model="dataForm.key" placeholder="parameter name" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button @click="getDataList()">Query</el-button>
            </el-form-item>
          </el-form>
          <el-table
            :data="dataList"
            border
            v-loading="dataListLoading"
            @selection-change="innerSelectionChangeHandle"
            style="width: 100%;"
          >
            <el-table-column type="selection" header-align="center" align="center"></el-table-column>
            <el-table-column prop="attrId" header-align="center" align="center" label="property id"></el-table-column>
            <el-table-column prop="attrName" header-align="center" align="center" label="property name"></el-table-column>
            <el-table-column prop="icon" header-align="center" align="center" label="property icon">
              <template slot-scope="scope">
              <img :src="scope.row.logo" style="width: 60px; height: 60px" />
            </template>
            </el-table-column>
            <el-table-column prop="valueSelect" header-align="center" align="center" label="List of optional values"></el-table-column>
          </el-table>
          <el-pagination
            @size-change="sizeChangeHandle"
            @current-change="currentChangeHandle"
            :current-page="pageIndex"
            :page-sizes="[10, 20, 50, 100]"
            :page-size="pageSize"
            :total="totalPage"
            layout="total, sizes, prev, pager, next, jumper"
          ></el-pagination>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="innerVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submitAddRealtion">Confirm Addition</el-button>
        </div>
      </el-dialog>
      <el-row>
        <el-col :span="24">
          <el-button type="primary" @click="addRelation">New Relation</el-button>
          <el-button
            type="danger"
            @click="batchDeleteRelation"
            :disabled="dataListSelections.length <= 0"
          >Batch delete</el-button>
          <!-- -->
          <el-table
            :data="relationAttrs"
            style="width: 100%"
            @selection-change="selectionChangeHandle"
            border
          >
            <el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
            <el-table-column prop="attrId" label="#"></el-table-column>
            <el-table-column prop="attrName" label="Attribute name"></el-table-column>
            <el-table-column prop="valueSelect" label="optional value">
              <template slot-scope="scope">
                <el-tooltip placement="top">
                  <div slot="content">
                    <span v-for="(i,index) in scope.row.valueSelect.split(';')" :key="index">
                      {{i}}
                      <br />
                    </span>
                  </div>
                  <el-tag>{{scope.row.valueSelect.split(";")[0]+" ..."}}</el-tag>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column fixed="right" header-align="center" align="center" label="action">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="relationRemove(scope.row.attrId)">Remove</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
//You can import other files here (such as: components, tool js, third-party plug-in js, json files, image files, etc.)
//For example: import "component name" from '"component path"';

export default {
  //The components introduced by import need to be injected into the object before they can be used
  components: {},
  props: {},
  data() {
    // store data here
    return {
      attrGroupId: 0,
      visible: false,
      innerVisible: false,
      relationAttrs: [],
      dataListSelections: [],
      dataForm: {
        key: ""
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      innerdataListSelections: []
    };
  },
  //Computed properties are similar to the data concept
  computed: {},
  //Monitor data changes in data
  watch: {},
  // method collection
  methods: {
    selectionChangeHandle(val) {
      this.dataListSelections = val;
    },
    innerSelectionChangeHandle(val) {
      this.innerdataListSelections = val;
    },
    addRelation() {
      this.getDataList();
      this.innerVisible = true;
    },
    batchDeleteRelation(val) {
      let postData = [];
      this.dataListSelections.forEach(item => {
        postData.push({ attrId: item.attrId, attrGroupId: this.attrGroupId });
      });
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(postData, false)
      }).then(({ data }) => {
        if (data.code == 0) {
          this.$message({ type: "success", message: "Delete successful" });
          this.init(this.attrGroupId);
        } else {
          this.$message({ type: "error", message: data.msg });
        }
      }).catch(() => {});
    },
    // remove association
    relationRemove(attrId) {
      let data = [];
      data.push({ attrId, attrGroupId: this.attrGroupId });
      this.$http({
        url: this.$http.adornUrl("/product/attrgroup/attr/relation/delete"),
        method: "post",
        data: this.$http.adornData(data, false)
      }).then(({ data }) => {
        if (data.code == 0) {
          this.$message({ type: "success", message: "Delete successful" });
          this.init(this.attrGroupId);
        } else {
          this.$message({ type: "error", message: data.msg });
        }
      }).catch(() => {});
    },
    submitAddRealtion() {
      this.innerVisible = false;
      // prepare data
      // console.log("Prepare new data", this.innerdataListSelections);
      if (this.innerdataListSelections.length > 0) {
        let postData = [];
        this.innerdataListSelections.forEach(item => {
          postData.push({ attrId: item.attrId, attrGroupId: this.attrGroupId });
        });
        this.$http({
          url: this.$http.adornUrl("/product/attrgroup/attr/relation"),
          method: "post",
          data: this.$http.adornData(postData, false)
        }).then(({ data }) => {
          if (data.code == 0) {
            this.$message({ type: "success", message: "Added association successfully" });
          }
          this.$emit("refreshData");
          this.init(this.attrGroupId);
        }).catch(() => {});
      } else {
      }
    },
    init(id) {
      this.attrGroupId = id || 0;
      this.visible = true;
      this.$http({
        url: this.$http.adornUrl(
          "/product/attrgroup/" + this.attrGroupId + "/attr/relation"
        ),
        method: "get",
        params: this.$http.adornParams({})
      }).then(({ data }) => {
        this.relationAttrs = data.data;
      }).catch(() => {});
    },
    dialogClose() {},

    //========
    // get data list
    getDataList() {
      this.dataListLoading = true;
      this.$http({
        url: this.$http.adornUrl(
          "/product/attrgroup/" + this.attrGroupId + "/noattr/relation"
        ),
        method: "get",
        params: this.$http.adornParams({
          page: this.pageIndex,
          limit: this.pageSize,
          key: this.dataForm.key
        })
      }).then(({ data }) => {
        if (data && data.code === 0) {
          this.dataList = data.data.list;
          this.totalPage = data.data.totalCount;
        } else {
          this.dataList = [];
          this.totalPage = 0;
        }
        this.dataListLoading = false;
      }).catch(() => {});
    },
    // per page
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // current page
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    }
  }
};
</script>
<style scoped>
</style>