(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-bdf00a5a"],{"333d":function(e,t,n){"use strict";var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"pagination-container",class:{hidden:e.hidden}},[n("el-pagination",e._b({attrs:{background:e.background,"current-page":e.currentPage,"page-size":e.pageSize,layout:e.layout,"page-sizes":e.pageSizes,total:e.total},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"update:pageSize":function(t){e.pageSize=t},"update:page-size":function(t){e.pageSize=t},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}},"el-pagination",e.$attrs,!1))],1)},a=[];n("c5f6");Math.easeInOutQuad=function(e,t,n,i){return e/=i/2,e<1?n/2*e*e+t:(e--,-n/2*(e*(e-2)-1)+t)};var l=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function o(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function r(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function s(e,t,n){var i=r(),a=e-i,s=20,u=0;t="undefined"===typeof t?500:t;var c=function e(){u+=s;var r=Math.easeInOutQuad(u,i,a,t);o(r),u<t?l(e):n&&"function"===typeof n&&n()};c()}var u={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(e){this.$emit("update:page",e)}},pageSize:{get:function(){return this.limit},set:function(e){this.$emit("update:limit",e)}}},methods:{handleSizeChange:function(e){this.$emit("pagination",{page:this.currentPage,limit:e}),this.autoScroll&&s(0,800)},handleCurrentChange:function(e){this.$emit("pagination",{page:e,limit:this.pageSize}),this.autoScroll&&s(0,800)}}},c=u,d=(n("5660"),n("2877")),p=Object(d["a"])(c,i,a,!1,null,"6af373ef",null);t["a"]=p.exports},3737:function(e,t,n){"use strict";n.d(t,"c",(function(){return a})),n.d(t,"d",(function(){return l})),n.d(t,"a",(function(){return o})),n.d(t,"b",(function(){return r}));var i=n("b775");function a(e){return Object(i["a"])({url:"/hippo4j/v1/cs/item/query/page",method:"post",data:e})}function l(e){return Object(i["a"])({url:"/hippo4j/v1/cs/item/update",method:"post",data:e})}function o(e){return Object(i["a"])({url:"/hippo4j/v1/cs/item/save",method:"post",data:e})}function r(e){return Object(i["a"])({url:"/hippo4j/v1/cs/item/delete/"+e[0]+"/"+e[1],method:"delete"})}},"395c":function(e,t,n){"use strict";n.r(t);var i=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"app-container"},[n("div",{staticClass:"filter-container"},[n("el-select",{staticClass:"filter-item",staticStyle:{width:"220px"},attrs:{placeholder:"租户 ID（必填）",filterable:""},on:{change:function(t){return e.tenantSelectList()}},model:{value:e.listQuery.tenantId,callback:function(t){e.$set(e.listQuery,"tenantId",t)},expression:"listQuery.tenantId"}},e._l(e.tenantOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1),e._v(" "),n("el-select",{staticClass:"filter-item",staticStyle:{width:"220px"},attrs:{placeholder:"项目 ID（必填）",filterable:""},on:{change:function(t){return e.itemSelectList()}},model:{value:e.listQuery.itemId,callback:function(t){e.$set(e.listQuery,"itemId",t)},expression:"listQuery.itemId"}},e._l(e.itemOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1),e._v(" "),n("el-select",{staticClass:"filter-item",staticStyle:{width:"220px"},attrs:{placeholder:"线程池标识（必填）",filterable:""},model:{value:e.listQuery.threadPoolKey,callback:function(t){e.$set(e.listQuery,"threadPoolKey",t)},expression:"listQuery.threadPoolKey"}},e._l(e.threadPoolKeyOptions,(function(e){return n("el-option",{key:e.key,attrs:{label:e.display_name,value:e.key}})})),1),e._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",attrs:{type:"primary",icon:"el-icon-search"},on:{click:e.fetchData}},[e._v("\n      搜索\n    ")]),e._v(" "),n("el-button",{directives:[{name:"waves",rawName:"v-waves"}],staticClass:"filter-item",staticStyle:{"margin-left":"10px"},attrs:{type:"primary",icon:"el-icon-refresh"},on:{click:e.refreshData}},[e._v("\n      重置\n    ")])],1),e._v(" "),n("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],attrs:{data:e.list,"element-loading-text":"Loading",stripe:"",border:"",fit:"","max-height":"714","highlight-current-row":""}},[n("el-table-column",{attrs:{label:"序号",width:"95"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.$index+1))]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"实例标识",width:"260"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-link",{attrs:{type:"primary",underline:!1}},[e._v(e._s(t.row.identify))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"Active"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-tag",{attrs:{type:e._f("statusFilter")(t.row.active)}},[e._v("\n          "+e._s(t.row.active)+"\n        ")])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"线程池标识"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n        "+e._s(t.row.threadPoolKey)+"\n      ")]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"核心线程"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-link",{attrs:{type:"success",underline:!1}},[e._v(e._s(t.row.coreSize))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"最大线程"},scopedSlots:e._u([{key:"default",fn:function(t){return[n("el-link",{attrs:{type:"danger",underline:!1}},[e._v(e._s(t.row.maximumSize))])]}}])}),e._v(" "),n("el-table-column",{attrs:{label:"操作",width:"180","class-name":"small-padding fixed-width"},scopedSlots:e._u([{key:"default",fn:function(t){var i=t.row;return[n("el-button",{attrs:{type:"text",size:"small"},on:{click:function(t){return e.handleUpdate(i)}}},[e._v("\n          编辑\n        ")])]}}])})],1),e._v(" "),n("el-dialog",{attrs:{title:e.textMap[e.dialogStatus],visible:e.dialogFormVisible},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[n("el-form",{ref:"dataForm",attrs:{rules:e.rules,model:e.temp,"label-position":"left","label-width":"110px"}},[n("el-form-item",{attrs:{label:"核心线程",prop:"coreSize"}},[[n("el-input-number",{attrs:{"controls-position":"right",min:1,max:9999},model:{value:e.temp.coreSize,callback:function(t){e.$set(e.temp,"coreSize",t)},expression:"temp.coreSize"}})]],2),e._v(" "),n("el-form-item",{attrs:{label:"最大线程",prop:"maximumSize"}},[[n("el-input-number",{attrs:{"controls-position":"right",min:1,max:9999},model:{value:e.temp.maximumSize,callback:function(t){e.$set(e.temp,"maximumSize",t)},expression:"temp.maximumSize"}})]],2),e._v(" "),n("el-form-item",{attrs:{label:"全部修改",prop:"allUpdate"}},[n("el-switch",{model:{value:e.temp.allUpdate,callback:function(t){e.$set(e.temp,"allUpdate",t)},expression:"temp.allUpdate"}})],1)],1),e._v(" "),n("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("\n        取消\n      ")]),e._v(" "),n("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.updateData()}}},[e._v("\n        确认\n      ")])],1)],1),e._v(" "),n("el-dialog",{attrs:{visible:e.dialogPluginVisible,title:"Reading statistics"},on:{"update:visible":function(t){e.dialogPluginVisible=t}}},[n("el-table",{staticStyle:{width:"100%"},attrs:{data:e.pluginData,border:"",fit:"","highlight-current-row":""}},[n("el-table-column",{attrs:{prop:"key",label:"Channel"}}),e._v(" "),n("el-table-column",{attrs:{prop:"pv",label:"Pv"}})],1),e._v(" "),n("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[n("el-button",{attrs:{type:"primary"},on:{click:function(t){e.dialogPvVisible=!1}}},[e._v("Confirm")])],1)],1)],1)},a=[],l=(n("ac6a"),n("456d"),n("3737")),o=n("dd71"),r=n("4d85"),s=n("47ec"),u=n("6724"),c=n("333d"),d=(n("bc3a"),{name:"JobProject",components:{Pagination:c["a"]},directives:{waves:u["a"]},filters:{statusFilter:function(e){var t={DEV:"info",TEST:"success",UAT:"warning",PROD:"danger"};return t[e]}},data:function(){var e=this;return{isRejectShow:!1,list:null,listLoading:!1,total:0,listQuery:{itemId:"",mark:"Hystrix",tenantId:"",threadPoolKey:""},pluginTypeOptions:["reader","writer"],dialogPluginVisible:!1,pluginData:[],dialogFormVisible:!1,runTimeTemp:{},typeOptions:[{key:"Dubbo",display_name:"Dubbo"},{key:"Kafka",display_name:"Kafka"},{key:"KafkaSpringCloudStream",display_name:"KafkaSpringCloudStream"},{key:"RocketMQ",display_name:"RocketMQ"},{key:"RocketMQSpringCloudStream",display_name:"RocketMQSpringCloudStream"},{key:"RabbitMQ",display_name:"RabbitMQ"},{key:"RabbitMQSpringCloudStream",display_name:"RabbitMQSpringCloudStream"}],tenantOptions:[],instanceDialogFormVisible:!1,threadPoolOptions:[],itemOptions:[],threadPoolKeyOptions:[],itemTempOptions:[],queueTypeOptions:[{key:1,display_name:"ArrayBlockingQueue"},{key:2,display_name:"LinkedBlockingQueue"},{key:3,display_name:"LinkedBlockingDeque"},{key:4,display_name:"SynchronousQueue"},{key:5,display_name:"LinkedTransferQueue"},{key:6,display_name:"PriorityBlockingQueue"},{key:9,display_name:"ResizableLinkedBlockingQueue (支持动态修改队列大小)"}],rejectedOptions:[{key:1,display_name:"CallerRunsPolicy"},{key:2,display_name:"AbortPolicy"},{key:3,display_name:"DiscardPolicy"},{key:4,display_name:"DiscardOldestPolicy"},{key:5,display_name:"RunsOldestTaskPolicy"},{key:6,display_name:"SyncPutQueuePolicy"},{key:99,display_name:"CustomRejectedPolicy（自定义 SPI 策略）"}],alarmTypes:[{key:1,display_name:"报警"},{key:0,display_name:"不报警"}],allowCoreThreadTimeOutTypes:[{key:1,display_name:"超时"},{key:0,display_name:"不超时"}],size:500,dialogStatus:"",textMap:{update:"Edit",create:"Create"},rules:{coreSize:[{required:!0,message:"this is required",trigger:"blur"}],maximumSize:[{required:!0,message:"this is required",trigger:"blur"},{validator:function(t,n,i){parseInt(n)<parseInt(e.temp.coreSize)&&i("最大线程必须大于等于核心线程"),i()}}]},temp:{id:void 0,tenantId:"",itemId:"",rejectedType:null,allUpdate:"1",customRejectedType:null},visible:!0}},created:function(){this.initSelect()},methods:{onInput:function(){this.$forceUpdate()},fetchData:function(){var e=this;null!=this.listQuery.mark&&0!=Object.keys(this.listQuery.mark).length?null!=this.listQuery.tenantId&&0!=Object.keys(this.listQuery.tenantId).length?null!=this.listQuery.itemId&&0!=Object.keys(this.listQuery.itemId).length?null!=this.listQuery.threadPoolKey&&0!=Object.keys(this.listQuery.threadPoolKey).length?(this.listLoading=!0,s["a"](this.listQuery).then((function(t){null==t&&(e.listLoading=!1),e.list=t,e.listLoading=!1}))):this.$message.warning("线程池标识不允许为空"):this.$message.warning("项目 ID 不允许为空"):this.$message.warning("租户 ID 不允许为空"):this.$message.warning("线程池类型不允许为空")},initSelect:function(){var e=this;o["c"]({size:this.size}).then((function(t){for(var n=t.records,i=0;i<n.length;i++)e.tenantOptions.push({key:n[i].tenantId,display_name:n[i].tenantId+" "+n[i].tenantName})}))},resetTemp:function(){this.isRejectShow=!1,this.temp={id:void 0,tenantId:"",itemId:"",rejectedType:null,customRejectedType:null}},updateData:function(){var e=this;this.$refs["dataForm"].validate((function(t){if(t){var n=[],i={mark:e.listQuery.mark,tenant:e.listQuery.tenantId,item:e.listQuery.itemId,threadPoolKey:e.temp.threadPoolKey,identify:e.temp.identify,clientAddressList:n,corePoolSize:e.temp.coreSize,maximumPoolSize:e.temp.maximumSize};if("0"===e.temp.allUpdate||void 0==e.temp.allUpdate||null==e.temp.allUpdate)n[0]=e.temp.clientAddress;else for(var a=0;a<e.list.length;a++)null!=e.list[a]&&(n[a]=e.list[a].clientAddress);e.updateExecute(i)}}))},updateExecute:function(e){var t=this;s["c"](e).then((function(e){t.dialogFormVisible=!1,t.$notify({title:"Success",message:"Update Successfully",type:"success",duration:2e3}),t.fetchData()})).catch((function(e){console.log(e),t.$message.error("修改线程池失败")}))},openDelConfirm:function(e){return this.$confirm("此操作将删除 ".concat(e,", 是否继续?"),"提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"})},selectQueueType:function(e){4===e?this.temp.capacity=0:5===e&&(this.temp.capacity=2147483647)},tenantSelectList:function(){var e=this;this.listQuery.itemId=null,this.listQuery.threadPoolKey=null,this.temp.itemId=null,this.itemOptions=[],this.itemTempOptions=[],this.threadPoolKeyOptions=[];var t={tenantId:this.listQuery.tenantId,size:this.size};l["c"](t).then((function(t){for(var n=t.records,i=0;i<n.length;i++)e.itemOptions.push({key:n[i].itemId,display_name:n[i].itemId+" "+n[i].itemName})}))},itemSelectList:function(){var e=this;this.listQuery.tpId=null,this.threadPoolKeyOptions=[];var t={mark:this.listQuery.mark,tenantId:this.listQuery.tenantId,itemId:this.listQuery.itemId};s["b"](t).then((function(t){for(var n=0;n<t.length;n++)e.threadPoolKeyOptions.push({key:t[n],display_name:t[n]})}))},handleUpdate:function(e){var t=this;this.temp=Object.assign({},e),this.dialogStatus="update",this.dialogFormVisible=!0,this.$nextTick((function(){t.$refs["dataForm"].clearValidate()}))},selectRejectedType:function(e){this.isRejectShow=99==e},handleInfo:function(e){this.instanceDialogFormVisible=!0,this.dialogStatus="info","undefined"==typeof e||null==e?e=this.tempRow:this.tempRow={clientAddress:e.clientAddress},this.refresh(e)},refreshData:function(){this.listQuery.mark=null,this.listQuery.tenantId=null,this.listQuery.itemId=null,this.listQuery.threadPoolKey=null,this.itemOptions=[],this.threadPoolKeyOptions=[]},refresh:function(e){var t=this,n="",i=e.clientAddress,a=e.clientBasePath;n=null!=a?i+a:i,r["j"]({clientAddress:n}).then((function(e){t.runTimeTemp=e})).catch((function(e){console.log(e),t.$message.error("查询失败，请尝试刷新页面")}))}}}),p=d,m=n("2877"),h=Object(m["a"])(p,i,a,!1,null,null,null);t["default"]=h.exports},"47ec":function(e,t,n){"use strict";n.d(t,"a",(function(){return a})),n.d(t,"b",(function(){return l})),n.d(t,"c",(function(){return o}));var i=n("b775");function a(e){return Object(i["a"])({url:"/hippo4j/v1/cs/adapter/thread-pool/query?mark="+e.mark+"&tenant="+e.tenantId+"&item="+e.itemId+"&threadPoolKey="+e.threadPoolKey,method:"get"})}function l(e){return Object(i["a"])({url:"/hippo4j/v1/cs/adapter/thread-pool/query/key?mark="+e.mark+"&tenant="+e.tenantId+"&item="+e.itemId,method:"get"})}function o(e){return Object(i["a"])({url:"/hippo4j/v1/cs/adapter/thread-pool/update",method:"post",data:e})}},"4d85":function(e,t,n){"use strict";n.d(t,"e",(function(){return a})),n.d(t,"f",(function(){return l})),n.d(t,"d",(function(){return o})),n.d(t,"g",(function(){return r})),n.d(t,"h",(function(){return s})),n.d(t,"j",(function(){return u})),n.d(t,"k",(function(){return c})),n.d(t,"i",(function(){return d})),n.d(t,"b",(function(){return p})),n.d(t,"c",(function(){return m})),n.d(t,"a",(function(){return h}));var i=n("b775");function a(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/query/page",method:"post",data:e})}function l(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/list/client/instance/"+e.itemId,method:"get",data:e})}function o(e){return Object(i["a"])({url:"/hippo4j/v1/cs/configs?tpId="+e.tpId+"&itemId="+e.itemId+"&namespace="+e.tenantId+"&instanceId="+e.identify,method:"get"})}function r(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/run/state/"+e.tpId+"?clientAddress="+e.clientAddress,method:"get"})}function s(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/run/thread/state/"+e.tpId+"?clientAddress="+e.clientAddress,method:"get"})}function u(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/web/run/state?clientAddress="+e.clientAddress,method:"get"})}function c(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/web/update/pool",method:"post",data:e})}function d(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/save_or_update",method:"post",data:e})}function p(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/save_or_update",method:"post",data:e})}function m(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/delete",method:"delete",data:e})}function h(e){return Object(i["a"])({url:"/hippo4j/v1/cs/thread/pool/alarm/enable/"+e.id+"/"+e.isAlarm,method:"post"})}},5660:function(e,t,n){"use strict";n("9cb6")},6724:function(e,t,n){"use strict";n("8d41");var i="@@wavesContext";function a(e,t){function n(n){var i=Object.assign({},t.value),a=Object.assign({ele:e,type:"hit",color:"rgba(0, 0, 0, 0.15)"},i),l=a.ele;if(l){l.style.position="relative",l.style.overflow="hidden";var o=l.getBoundingClientRect(),r=l.querySelector(".waves-ripple");switch(r?r.className="waves-ripple":(r=document.createElement("span"),r.className="waves-ripple",r.style.height=r.style.width=Math.max(o.width,o.height)+"px",l.appendChild(r)),a.type){case"center":r.style.top=o.height/2-r.offsetHeight/2+"px",r.style.left=o.width/2-r.offsetWidth/2+"px";break;default:r.style.top=(n.pageY-o.top-r.offsetHeight/2-document.documentElement.scrollTop||document.body.scrollTop)+"px",r.style.left=(n.pageX-o.left-r.offsetWidth/2-document.documentElement.scrollLeft||document.body.scrollLeft)+"px"}return r.style.backgroundColor=a.color,r.className="waves-ripple z-active",!1}}return e[i]?e[i].removeHandle=n:e[i]={removeHandle:n},n}var l={bind:function(e,t){e.addEventListener("click",a(e,t),!1)},update:function(e,t){e.removeEventListener("click",e[i].removeHandle,!1),e.addEventListener("click",a(e,t),!1)},unbind:function(e){e.removeEventListener("click",e[i].removeHandle,!1),e[i]=null,delete e[i]}},o=function(e){e.directive("waves",l)};window.Vue&&(window.waves=l,Vue.use(o)),l.install=o;t["a"]=l},"8d41":function(e,t,n){},"9cb6":function(e,t,n){},dd71:function(e,t,n){"use strict";n.d(t,"c",(function(){return a})),n.d(t,"d",(function(){return l})),n.d(t,"a",(function(){return o})),n.d(t,"b",(function(){return r}));var i=n("b775");function a(e){return Object(i["a"])({url:"/hippo4j/v1/cs/tenant/query/page",method:"post",data:e})}function l(e){return Object(i["a"])({url:"/hippo4j/v1/cs/tenant/update",method:"post",data:e})}function o(e){return Object(i["a"])({url:"/hippo4j/v1/cs/tenant/save",method:"post",data:e})}function r(e){return Object(i["a"])({url:"/hippo4j/v1/cs/tenant/delete/"+e,method:"delete"})}}}]);