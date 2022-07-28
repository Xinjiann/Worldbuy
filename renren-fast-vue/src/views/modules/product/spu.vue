<template>
  <div>
    <el-row>
      <el-col :span="24">
        <el-form :inline="true" :model="dataForm">
          <el-form-item label="category">
            <category-cascader :catelogPath.sync="catelogPath"></category-cascader>
          </el-form-item>
          <el-form-item label="Brand">
            <brand-select style="width:160px"></brand-select>
          </el-form-item>
          <el-form-item label="Status">
            <el-select style="width:160px" v-model="dataForm.status" clearable>
              <el-option label="New" :value="0"></el-option>
              <el-option label="On the shelf" :value="1"></el-option>
              <el-option label="Removal" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Search">
            <el-input style="width:160px" v-model="dataForm.key" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchSpuInfo">Query</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="24">
        <spuinfo :catId="catId"></spuinfo>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import CategoryCascader from "../common/category-cascader";
import BrandSelect from "../common/brand-select";
import Spuinfo from "./spuinfo";
export default {
  //The components introduced by import need to be injected into the object before they can be used
  components: { CategoryCascader, Spuinfo, BrandSelect },
  props: {},
  data() {
    // store data here
    return {
      catId: 0,
      catelogPath: [],
      dataForm: {
        status: "",
        key: "",
        brandId: 0,
        catelogId: 0
      },
      catPathSub: null,
      brandIdSub: null

    };
  },
  //Computed properties are similar to the data concept
  computed: {},
  //Monitor data changes in data
  watch: {},
  // method collection
  methods: {
    searchSpuInfo() {
      // console.log("Search criteria", this.dataForm);
      this.PubSub.publish("dataForm",this.dataForm);
    }
  },
  //Life cycle - creation completed (can access the current this instance)
  created() {},
  //Life cycle - mount completed (DOM elements can be accessed)
  mounted() {
    this.catPathSub = PubSub.subscribe("catPath", (msg, val) => {
      this.dataForm.catelogId = val[val.length-1];
    });
    this.brandIdSub = PubSub.subscribe("brandId", (msg, val) => {
      this.dataForm.brandId = val;
    });
  },
  beforeCreate() {}, //Life cycle - before creation
  beforeMount() {}, // life cycle - before mount
  beforeUpdate() {}, // life cycle - before update
  updated() {}, // life cycle - after update
  beforeDestroy() {
     PubSub.unsubscribe(this.catPathSub);
     PubSub.unsubscribe(this.brandIdSub);
  }, //Life cycle - before destruction
  destroyed() {}, //Life cycle - Destruction completed
  activated() {} //If the page has the keep-alive cache function, this function will trigger
};
</script>
<style scoped>
</style>