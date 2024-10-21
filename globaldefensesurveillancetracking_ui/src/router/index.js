import Vue from 'vue'
import VueRouter from 'vue-router'
import Nations from  '@/pages/Nations.vue';
import NationDetail from  '@/pages/NationDetail.vue';
import MilitaryBases from  '@/pages/MilitaryBases.vue';
import MilitaryBaseDetail from  '@/pages/MilitaryBaseDetail.vue';
import Soldiers from  '@/pages/Soldiers.vue';
import SoldierDetail from  '@/pages/SoldierDetail.vue';
import Equipments from  '@/pages/Equipments.vue';
import EquipmentDetail from  '@/pages/EquipmentDetail.vue';
import Activitys from  '@/pages/Activitys.vue';
import ActivityDetail from  '@/pages/ActivityDetail.vue';
import Movements from  '@/pages/Movements.vue';
import MovementDetail from  '@/pages/MovementDetail.vue';

Vue.use(VueRouter)

let routes = [
	{
		// will match everything
		path: '*',
		component: () => import('../views/404.vue'),
	},
	{
		path: '/',
		name: 'Home',
			redirect: '/nations',
							},
	{
		path: '/dashboard',
		name: 'Dashboard',
		layout: "dashboard",
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import(/* webpackChunkName: "dashboard" */ '../views/Dashboard.vue'),
	},
	{
		path: '/layout',
		name: 'Layout',
		layout: "dashboard",
		component: () => import('../views/Layout.vue'),
	},
	{
		path: '/nations',
		name: 'Nations',
		layout: "dashboard",
		component: Nations,
	},
	{
	    path: '/nation/:nationId', 
	    name: 'NationDetail',
		layout: "dashboard",
	    component: NationDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/militaryBases',
		name: 'MilitaryBases',
		layout: "dashboard",
		component: MilitaryBases,
	},
	{
	    path: '/militaryBase/:militaryBaseId', 
	    name: 'MilitaryBaseDetail',
		layout: "dashboard",
	    component: MilitaryBaseDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/soldiers',
		name: 'Soldiers',
		layout: "dashboard",
		component: Soldiers,
	},
	{
	    path: '/soldier/:soldierId', 
	    name: 'SoldierDetail',
		layout: "dashboard",
	    component: SoldierDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/equipments',
		name: 'Equipments',
		layout: "dashboard",
		component: Equipments,
	},
	{
	    path: '/equipment/:equipmentId', 
	    name: 'EquipmentDetail',
		layout: "dashboard",
	    component: EquipmentDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/activitys',
		name: 'Activitys',
		layout: "dashboard",
		component: Activitys,
	},
	{
	    path: '/activity/:activityId', 
	    name: 'ActivityDetail',
		layout: "dashboard",
	    component: ActivityDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/movements',
		name: 'Movements',
		layout: "dashboard",
		component: Movements,
	},
	{
	    path: '/movement/:movementId', 
	    name: 'MovementDetail',
		layout: "dashboard",
	    component: MovementDetail,
	    props: true // Pass route params as props to the component
  	},
	{
		path: '/requests/quickadd',
		name: 'QuickAdd',
		layout: "dashboard",
		meta: {
			title: 'quickadd',
			sidebarMap: ['applications'],
			breadcrumbs: ['Requests', 'QuickAdd'],
		},
		component: () => import('../pages/QuickAdd.vue'),
	},
	{
		path: '/tables',
		name: 'Tables',
		layout: "dashboard",
		component: () => import('../views/Tables.vue'),
	},
	{
		path: '/billing',
		name: 'Billing',
		layout: "dashboard",
		component: () => import('../views/Billing.vue'),
	},
	{
		path: '/rtl',
		name: 'RTL',
		layout: "dashboard-rtl",
		meta: {
			layoutClass: 'dashboard-rtl',
		},
		component: () => import('../views/RTL.vue'),
	},
	{
		path: '/Profile',
		name: 'Profile',
		layout: "dashboard",
		meta: {
			layoutClass: 'layout-profile',
		},
		component: () => import('../views/Profile.vue'),
	},
	{
		path: '/sign-in',
		name: 'Sign-In',
		component: () => import('../views/Sign-In.vue'),
	},
	{
		path: '/sign-up',
		name: 'Sign-Up',
		meta: {
			layoutClass: 'layout-sign-up',
		},
		component: () => import('../views/Sign-Up.vue'),
	},
]

// Adding layout property from each route to the meta
// object so it can be accessed later.
function addLayoutToRoute( route, parentLayout = "default" )
{
	route.meta = route.meta || {} ;
	route.meta.layout = route.layout || parentLayout ;
	
	if( route.children )
	{
		route.children = route.children.map( ( childRoute ) => addLayoutToRoute( childRoute, route.meta.layout ) ) ;
	}
	return route ;
}

routes = routes.map( ( route ) => addLayoutToRoute( route ) ) ;

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes,
	scrollBehavior (to, from, savedPosition) {
		if ( to.hash ) {
			return {
				selector: to.hash,
				behavior: 'smooth',
			}
		}
		return {
			x: 0,
			y: 0,
			behavior: 'smooth',
		}
	}
})

export default router
