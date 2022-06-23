<template>
  <el-dialog
    :title="!dataForm.id ? 'Add' : 'Modify'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="120px">
    <el-form-item label="member_id" prop="memberId">
      <el-input v-model="dataForm.memberId" placeholder="member_id"></el-input>
    </el-form-item>
    <el-form-item label="order number" prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="order number"></el-input>
    </el-form-item>
    <el-form-item label="Coupon used" prop="couponId">
      <el-input v-model="dataForm.couponId" placeholder="Coupon used"></el-input>
    </el-form-item>
    <el-form-item label="create_time" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="create_time"></el-input>
    </el-form-item>
    <el-form-item label="username" prop="memberUsername">
      <el-input v-model="dataForm.memberUsername" placeholder="username"></el-input>
    </el-form-item>
    <el-form-item label="Order total" prop="totalAmount">
      <el-input v-model="dataForm.totalAmount" placeholder="Order total"></el-input>
    </el-form-item>
    <el-form-item label="Amount payable" prop="payAmount">
      <el-input v-model="dataForm.payAmount" placeholder="Total amount payable"></el-input>
    </el-form-item>
    <el-form-item label="Freight Amount" prop="freightAmount">
      <el-input v-model="dataForm.freightAmount" placeholder="Freight Amount"></el-input>
    </el-form-item>
    <el-form-item label="Promotion optimization amount (promotion price, full discount, step price)" prop="promotionAmount">
      <el-input v-model="dataForm.promotionAmount" placeholder="Promotion optimization amount (promotion price, full discount, step price)"></el-input>
    </el-form-item>
    <el-form-item label="Point deduction amount" prop="integrationAmount">
      <el-input v-model="dataForm.integrationAmount" placeholder="Points deduction amount"></el-input>
    </el-form-item>
    <el-form-item label="Coupon Amount" prop="couponAmount">
      <el-input v-model="dataForm.couponAmount" placeholder="Coupon deduction amount"></el-input>
    </el-form-item>
    <el-form-item label="The discount amount used to adjust the order in the background" prop="discountAmount">
      <el-input v-model="dataForm.discountAmount" placeholder="Background adjustment of the discount amount used by the order"></el-input>
    </el-form-item>
    <el-form-item label="Payment method [1->Alipay; 2->WeChat; 3->UnionPay; 4->Cash on Delivery;]" prop="payType">
      <el-input v-model="dataForm.payType" placeholder="Payment method [1->Alipay; 2->WeChat; 3->UnionPay; 4->Cash on Delivery;]"></el-input>
    </el-form-item>
    <el-form-item label="Order Source [0->PC Order; 1->App Order]" prop="sourceType">
      <el-input v-model="dataForm.sourceType" placeholder="Order source [0->PC order; 1->app order]"></el-input>
    </el-form-item>
    <el-form-item label="Order status [0->Pending payment; 1->Pending delivery; 2->Shipped; 3->Completed; 4->Closed; 5->Invalid order] " prop="status">
      <el-input v-model="dataForm.status" placeholder="Order status [0->pending payment; 1->pending delivery; 2->delivered; 3->completed; 4->closed ;5->Invalid order]"></el-input>
    </el-form-item>
    <el-form-item label="Logistics company (delivery method)" prop="deliveryCompany">
      <el-input v-model="dataForm.deliveryCompany" placeholder="Logistics company (delivery method)"></el-input>
    </el-form-item>
    <el-form-item label="Logistics number" prop="deliverySn">
      <el-input v-model="dataForm.deliverySn" placeholder="delivery order number"></el-input>
    </el-form-item>
    <el-form-item label="Auto Confirmation Time (Days)" prop="autoConfirmDay">
      <el-input v-model="dataForm.autoConfirmDay" placeholder="Auto confirmation time (days)"></el-input>
    </el-form-item>
    <el-form-item label="Points earned" prop="integration">
      <el-input v-model="dataForm.integration" placeholder="Points earned"></el-input>
    </el-form-item>
    <el-form-item label="Achievable growth value" prop="growth">
      <el-input v-model="dataForm.growth" placeholder="Available growth value"></el-input>
    </el-form-item>
    <el-form-item label="Invoice Type [0->No Invoice; 1->Electronic Invoice; 2->Paper Invoice]" prop="billType">
      <el-input v-model="dataForm.billType" placeholder="Invoice Type [0->No Invoice; 1->Electronic Invoice; 2->Paper Invoice]"></el-input>
    </el-form-item>
    <el-form-item label="Invoice Header" prop="billHeader">
      <el-input v-model="dataForm.billHeader" placeholder="Invoice Header"></el-input>
    </el-form-item>
    <el-form-item label="Invoice Content" prop="billContent">
      <el-input v-model="dataForm.billContent" placeholder="Invoice Content"></el-input>
    </el-form-item>
    <el-form-item label="BillReceiverPhone" prop="billReceiverPhone">
      <el-input v-model="dataForm.billReceiverPhone" placeholder="BillReceiver Phone"></el-input>
    </el-form-item>
    <el-form-item label="BillReceiverEmail" prop="billReceiverEmail">
      <el-input v-model="dataForm.billReceiverEmail" placeholder="Billee Mailbox"></el-input>
    </el-form-item>
    <el-form-item label="Receiver Name" prop="receiverName">
      <el-input v-model="dataForm.receiverName" placeholder="receiver name"></el-input>
    </el-form-item>
    <el-form-item label="Receiver Phone" prop="receiverPhone">
      <el-input v-model="dataForm.receiverPhone" placeholder="Receiver Phone"></el-input>
    </el-form-item>
    <el-form-item label="Receiver Postcode" prop="receiverPostCode">
      <el-input v-model="dataForm.receiverPostCode" placeholder="Receiver Postcode"></el-input>
    </el-form-item>
    <el-form-item label="Province/Municipality" prop="receiverProvince">
      <el-input v-model="dataForm.receiverProvince" placeholder="province/municipality"></el-input>
    </el-form-item>
    <el-form-item label="City" prop="receiverCity">
      <el-input v-model="dataForm.receiverCity" placeholder="city"></el-input>
    </el-form-item>
    <el-form-item label="Region" prop="receiverRegion">
      <el-input v-model="dataForm.receiverRegion" placeholder="Region"></el-input>
    </el-form-item>
    <el-form-item label="Detailed address" prop="receiverDetailAddress">
      <el-input v-model="dataForm.receiverDetailAddress" placeholder="Detailed address"></el-input>
    </el-form-item>
    <el-form-item label="Order note" prop="note">
      <el-input v-model="dataForm.note" placeholder="Order Note"></el-input>
    </el-form-item>
    <el-form-item label="Confirm receipt status [0->unconfirmed; 1->confirmed]" prop="confirmStatus">
      <el-input v-model="dataForm.confirmStatus" placeholder="Confirm receipt status [0->unconfirmed; 1->confirmed]"></el-input>
    </el-form-item>
    <el-form-item label="Delete Status [0->Not deleted; 1->Deleted]" prop="deleteStatus">
      <el-input v-model="dataForm.deleteStatus" placeholder="Delete status [0->Not deleted; 1->Deleted]"></el-input>
    </el-form-item>
    <el-form-item label="Points to use when placing an order" prop="useIntegration">
      <el-input v-model="dataForm.useIntegration" placeholder="Points used when placing an order"></el-input>
    </el-form-item>
    <el-form-item label="Payment Time" prop="paymentTime">
      <el-input v-model="dataForm.paymentTime" placeholder="Payment Time"></el-input>
    </el-form-item>
    <el-form-item label="Delivery Time" prop="deliveryTime">
      <el-input v-model="dataForm.deliveryTime" placeholder="delivery time"></el-input>
    </el-form-item>
    <el-form-item label="Confirm receipt time" prop="receiveTime">
      <el-input v-model="dataForm.receiveTime" placeholder="Confirm receipt time"></el-input>
    </el-form-item>
    <el-form-item label="Comment Time" prop="commentTime">
      <el-input v-model="dataForm.commentTime" placeholder="comment time"></el-input>
    </el-form-item>
    <el-form-item label="modify time" prop="modifyTime">
      <el-input v-model="dataForm.modifyTime" placeholder="modify time"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">Cancel</el-button>
      <el-button type="primary" @click="dataFormSubmit()">OK</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          memberId: '',
          orderSn: '',
          couponId: '',
          createTime: '',
          memberUsername: '',
          totalAmount: '',
          payAmount: '',
          freightAmount: '',
          promotionAmount: '',
          integrationAmount: '',
          couponAmount: '',
          discountAmount: '',
          payType: '',
          sourceType: '',
          status: '',
          deliveryCompany: '',
          deliverySn: '',
          autoConfirmDay: '',
          integration: '',
          growth: '',
          billType: '',
          billHeader: '',
          billContent: '',
          billReceiverPhone: '',
          billReceiverEmail: '',
          receiverName: '',
          receiverPhone: '',
          receiverPostCode: '',
          receiverProvince: '',
          receiverCity: '',
          receiverRegion: '',
          receiverDetailAddress: '',
          note: '',
          confirmStatus: '',
          deleteStatus: '',
          useIntegration: '',
          paymentTime: '',
          deliveryTime: '',
          receiveTime: '',
          commentTime: '',
          modifyTime: ''
        },
        dataRule: {
          memberId: [
            { required: true, message: 'member_id不能为空', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: '订单号不能为空', trigger: 'blur' }
          ],
          couponId: [
            { required: true, message: '使用的优惠券不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: 'create_time不能为空', trigger: 'blur' }
          ],
          memberUsername: [
            { required: true, message: '用户名不能为空', trigger: 'blur' }
          ],
          totalAmount: [
            { required: true, message: '订单总额不能为空', trigger: 'blur' }
          ],
          payAmount: [
            { required: true, message: '应付总额不能为空', trigger: 'blur' }
          ],
          freightAmount: [
            { required: true, message: '运费金额不能为空', trigger: 'blur' }
          ],
          promotionAmount: [
            { required: true, message: '促销优化金额（促销价、满减、阶梯价）不能为空', trigger: 'blur' }
          ],
          integrationAmount: [
            { required: true, message: '积分抵扣金额不能为空', trigger: 'blur' }
          ],
          couponAmount: [
            { required: true, message: '优惠券抵扣金额不能为空', trigger: 'blur' }
          ],
          discountAmount: [
            { required: true, message: '后台调整订单使用的折扣金额不能为空', trigger: 'blur' }
          ],
          payType: [
            { required: true, message: '支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】不能为空', trigger: 'blur' }
          ],
          sourceType: [
            { required: true, message: '订单来源[0->PC订单；1->app订单]不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】不能为空', trigger: 'blur' }
          ],
          deliveryCompany: [
            { required: true, message: '物流公司(配送方式)不能为空', trigger: 'blur' }
          ],
          deliverySn: [
            { required: true, message: '物流单号不能为空', trigger: 'blur' }
          ],
          autoConfirmDay: [
            { required: true, message: '自动确认时间（天）不能为空', trigger: 'blur' }
          ],
          integration: [
            { required: true, message: '可以获得的积分不能为空', trigger: 'blur' }
          ],
          growth: [
            { required: true, message: '可以获得的成长值不能为空', trigger: 'blur' }
          ],
          billType: [
            { required: true, message: '发票类型[0->不开发票；1->电子发票；2->纸质发票]不能为空', trigger: 'blur' }
          ],
          billHeader: [
            { required: true, message: '发票抬头不能为空', trigger: 'blur' }
          ],
          billContent: [
            { required: true, message: '发票内容不能为空', trigger: 'blur' }
          ],
          billReceiverPhone: [
            { required: true, message: '收票人电话不能为空', trigger: 'blur' }
          ],
          billReceiverEmail: [
            { required: true, message: '收票人邮箱不能为空', trigger: 'blur' }
          ],
          receiverName: [
            { required: true, message: '收货人姓名不能为空', trigger: 'blur' }
          ],
          receiverPhone: [
            { required: true, message: '收货人电话不能为空', trigger: 'blur' }
          ],
          receiverPostCode: [
            { required: true, message: '收货人邮编不能为空', trigger: 'blur' }
          ],
          receiverProvince: [
            { required: true, message: '省份/直辖市不能为空', trigger: 'blur' }
          ],
          receiverCity: [
            { required: true, message: '城市不能为空', trigger: 'blur' }
          ],
          receiverRegion: [
            { required: true, message: '区不能为空', trigger: 'blur' }
          ],
          receiverDetailAddress: [
            { required: true, message: '详细地址不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '订单备注不能为空', trigger: 'blur' }
          ],
          confirmStatus: [
            { required: true, message: '确认收货状态[0->未确认；1->已确认]不能为空', trigger: 'blur' }
          ],
          deleteStatus: [
            { required: true, message: '删除状态【0->未删除；1->已删除】不能为空', trigger: 'blur' }
          ],
          useIntegration: [
            { required: true, message: '下单时使用的积分不能为空', trigger: 'blur' }
          ],
          paymentTime: [
            { required: true, message: '支付时间不能为空', trigger: 'blur' }
          ],
          deliveryTime: [
            { required: true, message: '发货时间不能为空', trigger: 'blur' }
          ],
          receiveTime: [
            { required: true, message: '确认收货时间不能为空', trigger: 'blur' }
          ],
          commentTime: [
            { required: true, message: '评价时间不能为空', trigger: 'blur' }
          ],
          modifyTime: [
            { required: true, message: '修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/order/order/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberId = data.order.memberId
                this.dataForm.orderSn = data.order.orderSn
                this.dataForm.couponId = data.order.couponId
                this.dataForm.createTime = data.order.createTime
                this.dataForm.memberUsername = data.order.memberUsername
                this.dataForm.totalAmount = data.order.totalAmount
                this.dataForm.payAmount = data.order.payAmount
                this.dataForm.freightAmount = data.order.freightAmount
                this.dataForm.promotionAmount = data.order.promotionAmount
                this.dataForm.integrationAmount = data.order.integrationAmount
                this.dataForm.couponAmount = data.order.couponAmount
                this.dataForm.discountAmount = data.order.discountAmount
                this.dataForm.payType = data.order.payType
                this.dataForm.sourceType = data.order.sourceType
                this.dataForm.status = data.order.status
                this.dataForm.deliveryCompany = data.order.deliveryCompany
                this.dataForm.deliverySn = data.order.deliverySn
                this.dataForm.autoConfirmDay = data.order.autoConfirmDay
                this.dataForm.integration = data.order.integration
                this.dataForm.growth = data.order.growth
                this.dataForm.billType = data.order.billType
                this.dataForm.billHeader = data.order.billHeader
                this.dataForm.billContent = data.order.billContent
                this.dataForm.billReceiverPhone = data.order.billReceiverPhone
                this.dataForm.billReceiverEmail = data.order.billReceiverEmail
                this.dataForm.receiverName = data.order.receiverName
                this.dataForm.receiverPhone = data.order.receiverPhone
                this.dataForm.receiverPostCode = data.order.receiverPostCode
                this.dataForm.receiverProvince = data.order.receiverProvince
                this.dataForm.receiverCity = data.order.receiverCity
                this.dataForm.receiverRegion = data.order.receiverRegion
                this.dataForm.receiverDetailAddress = data.order.receiverDetailAddress
                this.dataForm.note = data.order.note
                this.dataForm.confirmStatus = data.order.confirmStatus
                this.dataForm.deleteStatus = data.order.deleteStatus
                this.dataForm.useIntegration = data.order.useIntegration
                this.dataForm.paymentTime = data.order.paymentTime
                this.dataForm.deliveryTime = data.order.deliveryTime
                this.dataForm.receiveTime = data.order.receiveTime
                this.dataForm.commentTime = data.order.commentTime
                this.dataForm.modifyTime = data.order.modifyTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/order/order/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'memberId': this.dataForm.memberId,
                'orderSn': this.dataForm.orderSn,
                'couponId': this.dataForm.couponId,
                'createTime': this.dataForm.createTime,
                'memberUsername': this.dataForm.memberUsername,
                'totalAmount': this.dataForm.totalAmount,
                'payAmount': this.dataForm.payAmount,
                'freightAmount': this.dataForm.freightAmount,
                'promotionAmount': this.dataForm.promotionAmount,
                'integrationAmount': this.dataForm.integrationAmount,
                'couponAmount': this.dataForm.couponAmount,
                'discountAmount': this.dataForm.discountAmount,
                'payType': this.dataForm.payType,
                'sourceType': this.dataForm.sourceType,
                'status': this.dataForm.status,
                'deliveryCompany': this.dataForm.deliveryCompany,
                'deliverySn': this.dataForm.deliverySn,
                'autoConfirmDay': this.dataForm.autoConfirmDay,
                'integration': this.dataForm.integration,
                'growth': this.dataForm.growth,
                'billType': this.dataForm.billType,
                'billHeader': this.dataForm.billHeader,
                'billContent': this.dataForm.billContent,
                'billReceiverPhone': this.dataForm.billReceiverPhone,
                'billReceiverEmail': this.dataForm.billReceiverEmail,
                'receiverName': this.dataForm.receiverName,
                'receiverPhone': this.dataForm.receiverPhone,
                'receiverPostCode': this.dataForm.receiverPostCode,
                'receiverProvince': this.dataForm.receiverProvince,
                'receiverCity': this.dataForm.receiverCity,
                'receiverRegion': this.dataForm.receiverRegion,
                'receiverDetailAddress': this.dataForm.receiverDetailAddress,
                'note': this.dataForm.note,
                'confirmStatus': this.dataForm.confirmStatus,
                'deleteStatus': this.dataForm.deleteStatus,
                'useIntegration': this.dataForm.useIntegration,
                'paymentTime': this.dataForm.paymentTime,
                'deliveryTime': this.dataForm.deliveryTime,
                'receiveTime': this.dataForm.receiveTime,
                'commentTime': this.dataForm.commentTime,
                'modifyTime': this.dataForm.modifyTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
