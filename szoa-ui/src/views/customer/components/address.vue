<template>
  <div>
    <el-select ref="provinceSelect" v-model="chooseProvince" @change="choseProvince" class="province" placeholder="省">
      <el-option v-for="item in province" :key="item.id" :label="item.value" :value="item.id">
      </el-option>
    </el-select>
    <el-select ref="citySelect" v-model="chooseCity" @change="choseCity" placeholder="市">
      <el-option v-for="item in cities" :key="item.id" :label="item.value" :value="item.id">
      </el-option>
    </el-select>
    <el-select v-model="chooseCounty" @change="choseBlock" placeholder="区">
      <el-option v-for="item in counties" :key="item.id" :label="item.value" :value="item.id">
      </el-option>
    </el-select>
  </div>
</template>
   
<script>
import mapData from "./address.json"
export default {
  props: {
    selProvince: String,
    selCity: String,
    selCounty: String,
  },
  data() {
    return {
      province: '',
      cities: [],
      counties: [],
      city: '',
      block: '',
      //选择框选中的label值，因为此时value绑定的是id
      acProvince: '',
      acCity: '',
      acCouty: '',
      //把prop保存下来，进行修改赋值
      chooseProvince: this.selProvince,
      chooseCity: this.selCity,
      chooseCounty: this.selCounty,
    }
  },
  methods: {
    // 加载china地点数据，三级
    getCityData: function () {
      var that = this
      var data = mapData
      that.province = []
      that.city = []
      that.block = []
      // 省市区数据分类
      for (var item in data) {
        if (item.match(/0000$/)) {//省
          that.province.push({ id: item, value: data[item], children: [] })
        } else if (item.match(/00$/)) {//市
          that.city.push({ id: item, value: data[item], children: [] })
        } else {//区
          that.block.push({ id: item, value: data[item] })
        }
      }
      // 分类市级
      for (var index in that.province) {
        for (var index1 in that.city) {
          if (that.province[index].id.slice(0, 2) === that.city[index1].id.slice(0, 2)) {
            that.province[index].children.push(that.city[index1])
          }
        }
      }
      // 分类区级
      for (var item1 in that.city) {
        for (var item2 in that.block) {
          if (that.block[item2].id.slice(0, 4) === that.city[item1].id.slice(0, 4)) {
            that.city[item1].children.push(that.block[item2])
          }
        }
      }
    },
    // 选省
    choseProvince: function (e) {
      for (var index2 in this.province) {
        if (e === this.province[index2].id) {
          //    console.log(this.province[index2].id)//你选择的省级编码
          //   console.log(this.province[index2].value)//省级编码 对应的汉字 
          this.acProvince = this.province[index2].value
          this.cities = this.province[index2].children
          // this.chooseCity = this.province[index2].children[0].value
          this.chooseCity = ""
          this.counties = this.province[index2].children[0].children
          // this.chooseCounty = this.province[index2].children[0].children[0].value
          this.chooseCounty = ""
          this.E = this.cities[0].id
        }
      }
    },
    // 选市
    choseCity: function (e) {
      for (var index3 in this.city) {
        if (e === this.city[index3].id) {
          this.acCity = this.city[index3].value
          this.counties = this.city[index3].children
          // this.chooseCounty = this.city[index3].children[0].value
          this.chooseCounty = ""
          this.E = this.counties[0].id

        }
      }
    },
    // 选区
    choseBlock: function (e) {
      for (var index4 in this.block) {
        if (e === this.block[index4].id) {
          this.acCouty = this.block[index4].value
        }
      }
      this.E = e;
    },
  },
  created: function () {
    this.getCityData()
  },
  watch: {
    acProvince(val) {
      this.$emit('changeName', this.acProvince)
      this.acProvince = val
      this.$refs.provinceSelect.visible = false
    },
    acCity(val) {
      this.$emit('changeNames', this.acCity)
      this.acCity = val
      this.$refs.citySelect.visible = false
    },
    acCouty(val) {
      this.acCouty = val
      this.$emit('changeRegion', this.acCouty)
    },

    counties(val) {
      this.$emit('changeRegion', this.acCouty)
    },
    selProvince: function (newValue) {
      this.chooseProvince = newValue;
    },
    selCity: function (newValue) {
      this.chooseCity = newValue;
    },
    selCounty: function (newValue) {
      this.chooseCounty = newValue;
    }

  },
}
</script>
   
<style scoped>
.province {
  margin-right: 20px;
}
</style>