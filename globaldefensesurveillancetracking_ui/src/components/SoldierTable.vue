
<template>
  <div class="content">
    <!-- search bar -->
    <div class="row my-3">
      <div class="col-8"></div>
      <div class="col-4">
        <!-- Header Search Input -->
        <a-input-search class="header-search" :class="searchLoading ? 'loading' : ''" placeholder="Search by BusinessUnit#, Location#, Operator#, City, State, FirstName, LastName…"
          @search="onSearch" :loading='searchLoading' v-model="searchQuery">
          <svg slot="prefix" width="16" height="16" viewBox="0 0 20 20" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" clip-rule="evenodd"
              d="M8 4C5.79086 4 4 5.79086 4 8C4 10.2091 5.79086 12 8 12C10.2091 12 12 10.2091 12 8C12 5.79086 10.2091 4 8 4ZM2 8C2 4.68629 4.68629 2 8 2C11.3137 2 14 4.68629 14 8C14 9.29583 13.5892 10.4957 12.8907 11.4765L17.7071 16.2929C18.0976 16.6834 18.0976 17.3166 17.7071 17.7071C17.3166 18.0976 16.6834 18.0976 16.2929 17.7071L11.4765 12.8907C10.4957 13.5892 9.29583 14 8 14C4.68629 14 2 11.3137 2 8Z"
              fill="#111827" />
          </svg>
        </a-input-search>
        <!-- / Header Search Input -->
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <card>
          <template slot="header">
            <div class="row justify-content-between align-items-between mx-3">

              <h5 class="card-title">Soldiers</h5>
              
              <div>
                  <base-button class="btn btn-primary" @click="modalSoldiers = true">Add</base-button>
              </div>
              
              <modal :show.sync="modalSoldiers">
                <template slot="header">
                  <h5 class="modal-title" id="exampleModalLabel">Add Soldier</h5>
                </template>
                <div>
                  <form @submit.prevent>
  <base-input label="SoldierId" type="text" placeholder="Enter SoldierId" v-model="soldierToAdd.soldierId"></base-input>
  <base-input label="Name" type="text" placeholder="Enter Name" v-model="soldierToAdd.name"></base-input>
  <base-input label="Rank" type="text" placeholder="Enter Rank" v-model="soldierToAdd.rank"></base-input>
                  </form>
                </div>
                <template slot="footer">
                  <base-button type="primary" @click="handleAddSubmitted()">Save</base-button>
                </template>
              </modal>
            </div>
          </template>


          <!-- Conditionally render the view based on the 'isTableView' flag -->
          <div v-if="isTableView">
            <!-- Render the existing Table View -->
            <a-table :columns="columns" :data-source="soldiers" :row-key="record => record.SoldierId" :pagination="pagination"
              :loading="searchLoading" @change="onTableChange" :scroll="{ x: 'max-content' }">



             <template slot="lastModified" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="createdOn" slot-scope="dataIndex">
              	{{ formatTime(dataIndex) }}
              </template>
              <template slot="blackOutStartDate" slot-scope="dataIndex">
              	{{ formatDate(dataIndex) }}
              </template>
            </a-table>
          </div>
          <div v-else>
            <!-- Render the Picture View  -->
            <SoldierPictureView :soldiers="soldiers" />
          </div>

        </card>
      </div>
    </div>

  </div>
</template>

<script>
import Modal from "@/components/Modal";
import BaseButton from "@/components/BaseButton";
import BaseInput from "@/components/Inputs/BaseInput";
import NotificationTemplate from "@/pages/Notifications/NotificationTemplate";
import { Card } from "@/components/index";
import SoldierService from "../services/SoldierService";
import { ASelect, ASelectOption, AButton, Table, Pagination } from "ant-design-vue";
import SoldierPictureView from './SoldierPictureView.vue';


const soldiersColumns = [
  "soldierId",
  "year",
  "date",
  "competitionId",
  "soldierId"
]

export default {
  props: {
    soldiers: {
      type: Array,
      required: true,
    },
    totalElements: {
      type: Number,
      required: true,
    },
    page: {
      type: Number,
      required: true,
    },
  },
  components: {
    Card,
    Modal,
    BaseButton,
    BaseInput,
    Table,

    Pagination,
    SoldierPictureView
  },

  data() {
    return {
      modalSoldiers: false,
        isTableView: true,

      columns: [
        {
          title: 'Soldier Id',
		dataIndex: 'soldierId',
          visible: true,
          scopedSlots: { customRender: 'soldierId' },
          sorter: true
          //sorter: (a, b) => a.soldierId - b.soldierId,
          //sorter: (a, b) => a.soldierId.localeCompare(b.soldierId),
        },
        {
          title: 'Name',
		dataIndex: 'name',
          visible: true,
          scopedSlots: { customRender: 'name' },
          sorter: true
          //sorter: (a, b) => a.name - b.name,
          //sorter: (a, b) => a.name.localeCompare(b.name),
        },
        {
          title: 'Rank',
		dataIndex: 'rank',
          visible: true,
          scopedSlots: { customRender: 'rank' },
          sorter: true
          //sorter: (a, b) => a.rank - b.rank,
          //sorter: (a, b) => a.rank.localeCompare(b.rank),
        },
      ],
      pagination: {
        current: 1,
        pageSize: 50,
        total: 0,
        showSizeChanger: true,
        showQuickJumper: true,
        showTotal: (total) => `Total ${total} soldiers`,
      },

      soldiers: [],
      soldierToAdd: {},

      soldiersTable: {
        columns: [...soldiersColumns],
        data: [],
      },

      // New properties for sorting and searching
      sortBy: 'soldierId',           // Column to sort by
      sortOrder: 'asc',     // Sort order (asc or desc)
      searchQuery: '',      // Search query
      searchLoading: false, // Initialize searchLoading property


    };
  },
  watch: {
    searchQuery: {
      handler: "handleSearchQueryChanged", // Call the fetchData method when searchQuery changes
      immediate: true, // Trigger immediately when the component is mounted
    },
  },

  methods: {

    async renderSoldiersTable() {
      this.searchLoading = true; // Set searchLoading to true while fetching data
      this.searchLoading = false;

      this.pagination.total = this.totalElements;
      this.pagination.current = this.page;

      let soldiersTableData = [];
      for (let i = 0; i < this.soldiers.length; i++) {
        soldiersTableData.push({
          id: i,
          soldierId: this.soldiers[i].soldierId,
          year: this.soldiers[i].year,
          date: this.soldiers[i].date,
          competitionId: this.soldiers[i].competitionId,
          soldierId: this.soldiers[i].soldierId,
        });

      }
      this.searchLoading = false;
    },

    async onTableChange(pagination, filters, sorter) {
      if (sorter && sorter.field && sorter.order) {
        this.sortBy = sorter.field;
        if (sorter.order == "ascend") {
            this.sortOrder = "asc";
        } else {
            this.sortOrder = "desc";
        }
      }
      if (pagination) {
        this.pagination.current = pagination.current;
        this.pagination.pageSize = pagination.pageSize;
      }

	  this.$emit('get-all-soldiers',this.sortBy,this.sortOrder,this.pagination.current-1,this.pagination.pageSize);
      this.handleTableChanged()
    },
	
	initializeColumns() {
        this.columns = this.columns.filter(item => item.visible);
    },

    routingToNationDetail(id) {
      this.$router.push({ name: 'NationDetail', params: { nationId: id.toString() }})
    },
    routingToMilitaryBaseDetail(id) {
      this.$router.push({ name: 'MilitaryBaseDetail', params: { militaryBaseId: id.toString() }})
    },
    routingToSoldierDetail(id) {
      this.$router.push({ name: 'SoldierDetail', params: { soldierId: id.toString() }})
    },
    routingToEquipmentDetail(id) {
      this.$router.push({ name: 'EquipmentDetail', params: { equipmentId: id.toString() }})
    },
    routingToActivityDetail(id) {
      this.$router.push({ name: 'ActivityDetail', params: { activityId: id.toString() }})
    },
    routingToMovementDetail(id) {
      this.$router.push({ name: 'MovementDetail', params: { movementId: id.toString() }})
    },
    
    handleSearchQueryChanged() {
    	console.log("handleSearchQueryChanged CALLED FROM @search")
    	this.$root.$emit('searchQueryForSoldiersChanged', this.searchQuery);
		//this.renderSoldiersTable();
    },

    onSearch(value) {
      console.log(value);
      this.searchQuery = value; // Update searchQuery when the user types
    },

    toggleView() {
      this.isTableView = !this.isTableView;
    },
	
	async handleAddSubmitted() {
      this.modalSoldiers = false;

      const currentDate = new Date().getTime();
      this.soldierToAdd.created = currentDate;

      const jsonData = JSON.stringify(this.soldierToAdd);
      console.log(jsonData);
      
      const res = await SoldierService.addSoldier(jsonData);

      if (res.status === 200) {
        this.$notify({
          component: NotificationTemplate,
          icon: "tim-icons icon-bell-55",
          type: "success",
          timeout: 3000,
        });
      }
	},
	
	handleTableChanged() {
	},
	
	formatTime(dateString) {
      if(dateString !== null){
        const date = new Date(dateString);
      return `${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}-${date.getFullYear()} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')} EST`;
      }
      // Format the date here as needed, for example:
    },  
    
 formatDate(dateString) {
    if (dateString !== null) {
	    const date = new Date(dateString);
	    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
	    const day = String(date.getDate()).padStart(2, '0');
	    const monthAbbreviation = months[date.getMonth()];
	    const year = date.getFullYear();
	    return `${day} ${monthAbbreviation} ${year}`;
  	}
  	// Handle the case when dateString is null or invalid
  	return '';
    
   },

  },
  mounted() {
  	this.initializeColumns();
    this.renderSoldiersTable();
  }
};
</script>

<style>
.modal-dialog {
  margin-top: -300px;
}
.ant-table-row  {
	text-align: center;
}
.ant-table-thead th span {
	text-align: center;
	width: 100%
}
.ant-table-fixed td span {
    text-align: center;
}

</style>
