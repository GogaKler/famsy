import { type Ref, ref } from 'vue';


type TUserRoute = {  
  icon: string;  
  title: string;  
  routeName: string;  
};  
  
export const userRoutes: Ref<TUserRoute[]> = ref([  
  {  
    icon: 'user',  
    title: 'Профиль',  
    routeName: 'profile', 
  },  
]);  