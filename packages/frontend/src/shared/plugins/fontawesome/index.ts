import { type App, type Plugin } from 'vue';
import { library } from '@fortawesome/fontawesome-svg-core';
// SOLID
import {
  faArrowLeft,
  faArrowRightFromBracket,
  faBars,
  faChevronDown,
  faCircleExclamation,
  faEllipsis,
  faExclamation,
  faHome,
  faPencil,
  faSpinner,
  faTriangleExclamation,
  faUserSecret,
  faXmark,
} from '@fortawesome/free-solid-svg-icons';
// REGULAR
import {
  faLightbulb,
  faMessage,
  faMoon,
  faSquare,
  faSun,
  faUser,
} from '@fortawesome/free-regular-svg-icons';
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
    );

    library.add(faLightbulb, faMoon, faSun, faMessage, faSquare, faUser);
    
    app.component('FontAwesomeIcon', FontAwesomeIcon);
  },
};