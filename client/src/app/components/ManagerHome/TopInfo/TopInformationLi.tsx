interface TopInformationLiProps {
  label: string;
  description: string;
}

export default function TopInformationLi({
  label,
  description,
}: TopInformationLiProps) {
  return (
    <div>
      <span className="flex">
        <label className="w-28">{label}</label>
        <span>{description}</span>
      </span>
    </div>
  );
}
