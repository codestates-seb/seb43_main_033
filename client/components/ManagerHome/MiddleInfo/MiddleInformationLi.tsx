interface MiddleInformationLiProps {
  label: string;
  value: string;
}

export default function MiddleInformationLi({
  label,
  value,
}: MiddleInformationLiProps) {
  return (
    <div className="flex">
      <label className="w-36 mb-1">{label}</label>
      <span>{value}</span>
    </div>
  );
}
