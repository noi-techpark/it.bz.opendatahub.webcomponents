import { Dist } from '../struct/Dist';
import { Configuration } from '../struct/Configuration';

export interface WebcomponentConfigurationModel {
  webcomponentUuid: string;

  deliveryBaseUrl: string;

  dist: Dist;

  configuration: Configuration;

  scriptSources: Array<string>;
}
