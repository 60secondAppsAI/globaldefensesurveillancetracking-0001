<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <militaryBase-table
            v-if="militaryBases && militaryBases.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:militaryBases="militaryBases"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-military-bases="getAllMilitaryBases"
             >

            </militaryBase-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import MilitaryBaseTable from "@/components/MilitaryBaseTable";
import MilitaryBaseService from "../services/MilitaryBaseService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    MilitaryBaseTable,
  },
  data() {
    return {
      militaryBases: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllMilitaryBases(sortBy='militaryBaseId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await MilitaryBaseService.getAllMilitaryBases(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.militaryBases.length) {
					this.militaryBases = response.data.militaryBases;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching militaryBases:", error);
        }
        
      } catch (error) {
        console.error("Error fetching militaryBase details:", error);
      }
    },
  },
  mounted() {
    this.getAllMilitaryBases();
  },
  created() {
    this.$root.$on('searchQueryForMilitaryBasesChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllMilitaryBases();
    })
  }
};
</script>
<style></style>
