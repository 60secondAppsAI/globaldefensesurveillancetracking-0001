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
            <nation-table
            v-if="nations && nations.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:nations="nations"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-nations="getAllNations"
             >

            </nation-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/index";

import NationTable from "@/components/NationTable";
import NationService from "../services/NationService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    NationTable,
  },
  data() {
    return {
      nations: [],
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
    async getAllNations(sortBy='nationId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await NationService.getAllNations(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.nations.length) {
					this.nations = response.data.nations;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching nations:", error);
        }
        
      } catch (error) {
        console.error("Error fetching nation details:", error);
      }
    },
  },
  mounted() {
    this.getAllNations();
  },
  created() {
    this.$root.$on('searchQueryForNationsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllNations();
    })
  }
};
</script>
<style></style>
