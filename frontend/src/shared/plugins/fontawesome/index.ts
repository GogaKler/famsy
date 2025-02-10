import { type App, type Plugin } from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
// SOLID
import {
  faArrowLeft,
  faArrowRightFromBracket,
  faBars,
  faChartPie,
  faChevronDown,
  faCircleExclamation,
  faEllipsis,
  faExclamation,
  faHome,
  faLock,
  faPencil,
  faSpinner,
  faTriangleExclamation,
  faUser,
  faUserSecret,
  faWallet,
  faXmark,
  faTableColumns,
  faBell,
  faPlus,
  faAngleRight,
  faPeopleRoof,
  faFolder,
  faStore,
  faFlag,
  faClockRotateLeft,
} from '@fortawesome/free-solid-svg-icons';
// REGULAR
import { faLightbulb, faMessage, faMoon, faSquare, faSun } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';


export const FsPluginFontawesome: Plugin = {
  install(app: App): void {
    library.add(
      faUserSecret,
      faHome,
      faBars,
      faTriangleExclamation,
      faSpinner,
      faCircleExclamation,
      faExclamation,
      faEllipsis,
      faChevronDown,
      faArrowRightFromBracket,
      faArrowLeft,
      faXmark,
      faPencil,
      faBell,
      faPlus,
      faTableColumns,
      faAngleRight,
      faPeopleRoof,
      faFolder,
      faStore,
      faFlag,
      faClockRotateLeft,
    );

    
    library.add(faLightbulb, faMoon, faSun, faMessage, faSquare, faUser, faChartPie, faWallet, faUser, faLock);
    
    app.component('FontAwesomeIcon', FontAwesomeIcon);
  },
};