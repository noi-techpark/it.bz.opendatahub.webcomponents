export interface BvSelectOption {
  value: boolean | number | string | null;
  text?: string;
  disabled?: boolean;
}

export type BvSelectOptionArray = Array<BvSelectOption>;
